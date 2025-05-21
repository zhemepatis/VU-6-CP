#include <stdio.h>
#include <mpi.h>
#include <stdlib.h>
#include <time.h>

#define ROOT_RANK 0

void computeSplitBounds(int* start, int* end, int totalRows, int processRank, int totalProcesses);
int* initGrid(int size);

void printGrid(int* grid, int width, int height);
void printIteration(int* grid, int width, int height, int iteration);

int countActiveNeighbors(int x, int y, int width, int height, int* grid);
int getNextCellState(int state, int activeNeighborCount);
int isInBounds(int x, int y, int width, int height);

int main(int argc, char* argv[]) {
	srand(time(NULL));

	MPI_Init(&argc, &argv);

	// get program arguments
	int width = atoi(argv[1]);
	int height = atoi(argv[2]);
	int verbose = atoi(argv[3]);
	int iterations = 10;

	// get MPI arguments
	int processRank, totalProcesses;
	MPI_Comm_rank(MPI_COMM_WORLD, &processRank);
	MPI_Comm_size(MPI_COMM_WORLD, &totalProcesses);

	// get rows to compute
	int firstRow, lastRow, totalRows, totalCells;
	computeSplitBounds(&firstRow, &lastRow, height, processRank, totalProcesses);
	totalRows = lastRow - firstRow + 1;
	totalCells = totalRows * width;

	// get info for data distribution
	int* grid = NULL;
	int* rowsPerProcess = NULL;
	int* rowStartOffsets = NULL;

	if (processRank == ROOT_RANK) {
		// initialise grid
		grid = initGrid(width * height);

		if (verbose == 1) {
			printIteration(grid, width, height, 0);
		}

		// get split sizes
		rowStartOffsets = malloc(totalProcesses * sizeof(int));
		rowsPerProcess = malloc(totalProcesses * sizeof(int));

		for (int i = 0; i < totalProcesses; ++i) {
			int tempFirstRow, tempLastRow;
			computeSplitBounds(&tempFirstRow, &tempLastRow, height, i, totalProcesses);
			
			rowsPerProcess[i] = (tempLastRow - tempFirstRow + 1) * width;
			rowStartOffsets[i] = tempFirstRow * width;
		}
	}

	// get expanded board


	// braodcast the board
	int offset = (processRank == 0) ? 0 : width;  
	int expandedSize = (processRank == 0 || processRank == totalProcesses - 1) ? (totalRows + 1) * width : (totalRows + 2) * width;
	int* expandedGrid = malloc(expandedSize * sizeof(int));
	int* resultBuff = malloc(totalCells * sizeof(int));

	MPI_Scatterv(grid, rowsPerProcess, rowStartOffsets, MPI_INT, expandedGrid + offset, totalCells, MPI_INT, ROOT_RANK, MPI_COMM_WORLD);

	// get buffers for upper & bottom rows
	int* upperRowBuff = malloc(width * sizeof(int));
	int* bottomRowBuff = malloc(width * sizeof(int));

	int dstRank, srcRank;

	// start calculations
	for (int iteration = 1; iteration <= iterations; ++iteration) {
		// send upper lines
		// TODO: might not work as there is expanded grid now
		dstRank = (processRank + 1) % totalProcesses;
		srcRank = processRank == 0 ? totalProcesses - 1 : processRank - 1;
		MPI_Sendrecv(expandedGrid + (totalRows - 1) * width, width, MPI_INT, dstRank, 0,
					upperRowBuff, width, MPI_INT, srcRank, 0, 
					MPI_COMM_WORLD, MPI_STATUS_IGNORE);

		// send bottom lines
		// TODO: might not work as there is expanded grid now
		dstRank = processRank == 0 ? totalProcesses - 1 : processRank - 1;
		srcRank = (processRank + 1) % totalProcesses;
		MPI_Sendrecv(expandedGrid, width, MPI_INT, dstRank, 0,
					bottomRowBuff, width, MPI_INT, srcRank, 0, 
					MPI_COMM_WORLD, MPI_STATUS_IGNORE);
		
		

		// calculate
		int rowOffset = (offset == 0) ? 0 : 1;
		for (int rowIdx = rowOffset; rowIdx < totalRows; ++rowIdx) {
			for (int cellIdx = 0; cellIdx < width; ++cellIdx) {
				int rowLowerBound = (processRank < totalProcesses - 1) ? totalRows + 1: totalRows;
				int activeNeighbors = countActiveNeighbors(cellIdx, rowIdx, width, rowLowerBound, expandedGrid);
				int state = expandedGrid[rowIdx * width + cellIdx];
				int nextState = getNextCellState(state, activeNeighbors);
				resultBuff[(rowIdx - rowOffset) * width + cellIdx] = nextState;
			}
		}

		memcpy(expandedGrid + offset, resultBuff, sizeof(int));

		// gather rows


		// print iteration
		if (processRank == ROOT_RANK && verbose == 1) {
			printIteration(grid, width, height, iteration);
		}
	}

	// free resources
	if (processRank == ROOT_RANK) {
		free(rowsPerProcess);
		free(rowStartOffsets);
		free(grid);
	}


	MPI_Finalize();
}

void computeSplitBounds(int* start, int* end, int totalRows, int processRank, int totalProcesses) {

    int baseElementsPerProcess = totalRows / totalProcesses;
    int remainingElements = totalRows % totalProcesses;

    int hasExtraElement = (processRank < remainingElements) ? 1 : 0;
    *start = processRank * baseElementsPerProcess + (processRank < remainingElements ? processRank : remainingElements);
    *end = *start + baseElementsPerProcess + hasExtraElement - 1;
}

int* initGrid(int size) {
	int* grid = malloc(size * sizeof(int));

	for(int i = 0; i < size; ++i) {
		grid[i] = rand() % 2;
	}

	return grid;
}

void printGrid(int* grid, int width, int height) {
	for(int i = 0; i < height; ++i) {
		for(int j = 0; j < width; ++j) {
			int isActive = grid[i * width + j];

			if (isActive) {
				printf("\x1b[34m" "%d " "\x1b[0m", isActive);
			}
			else {
				printf("%d ", isActive);
			}
		}

		printf("\n");
	}
}

void printIteration(int* grid, int width, int height, int iteration) {
	printf("ITERATION %d\n", iteration);
	printGrid(grid, width, height);
	getchar();
}

int countActiveNeighbors(int x, int y, int width, int height, int* grid) {
 	int directions[8][2] = {{0, -1}, {1, -1}, {1, 0}, {1, 1}, {0, 1}, {-1, 1}, {-1, 0}, {-1, -1}};
	int count = 0;

	for(int i = 0; i < 8; ++i) {
		int newX = x + directions[i][0];
		int newY = y + directions[i][1];

		if (isInBounds(newX, newY, width, height) && grid[newY * width + newX]) {
			++count;
		}
	}
    
	return count;
}

int getNextCellState(int state, int activeNeighborCount) {
	int nextState;

	if (state) {
		nextState = activeNeighborCount == 2 || activeNeighborCount == 3;
	} 
	else {
		nextState = activeNeighborCount == 3;
	}

	return nextState;
}

int isInBounds(int x, int y, int width, int height) {
	return x >= 0 && x < width && y >= 0 && y < height;
}