#include <stdio.h>
#include <mpi.h>
#include <stdlib.h>
#include <time.h>
#include "headers/task-manager.h"
#include "headers/calculation-task.h"
#include "headers/board.h"

int main(int argc, char* argv[]) {
	srand(time(NULL));

	int processRank;
	int totalProcesses;

	MPI_Init(&argc, &argv);
	MPI_Comm_rank(MPI_COMM_WORLD, &processRank);
	MPI_Comm_size(MPI_COMM_WORLD, &totalProcesses);

	if (processRank == 0) {
		int x = atoi(argv[1]);
		int y = atoi(argv[2]);
		int iterations = 1000;
		int verbose = atoi(argv[3]);

		Board board = initBoard(x, y);

		taskManager(board, iterations, verbose);

		freeBoard(board);
	} 
	else {
		int width = atoi(argv[1]);
		int height = atoi(argv[2]);
		int totalCells = height * width;
		
		int partitionStart, partitionEnd;
		computePartitionBounds(&partitionStart, &partitionEnd, totalCells, processRank, totalProcesses);

		// start calculation task
		calculationTask(partitionStart, partitionEnd, width, height);
	}

	MPI_Finalize();
}