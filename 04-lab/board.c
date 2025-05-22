#include <stdlib.h>
#include <stdio.h>
#include "headers/board.h"

Board initBoard(int width, int height) {
	Board board;
	
	board.width = width;
	board.height = height;
	board.cells = malloc(width * height * sizeof(int));

	for(int i = 0; i < height; ++i) {
		for(int j = 0; j < width; ++j) {
			board.cells[i * width + j] = rand() % 2;
		}
	}

	return board;
}

void printBoard(Board board) {
	for(int i = 0; i < board.height; ++i) {
		for(int j = 0; j < board.width; ++j) {
			int isActive = board.cells[i * board.width + j];

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

void freeBoard(Board board) {
	free(board.cells);
}

int countActiveNeighbors(int x, int y, Board board) {
 	int directions[8][2] = {{0, -1}, {1, -1}, {1, 0}, {1, 1}, {0, 1}, {-1, 1}, {-1, 0}, {-1, -1}};
	int count = 0;

	for(int i = 0; i < 8; ++i) {
		int newX = x + directions[i][0];
		int newY = y + directions[i][1];

		if (isInBounds(newX, newY, board) && board.cells[newY * board.width + newX]) {
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

int isInBounds(int x, int y, Board board) {
	return x >= 0 && x < board.width && y >= 0 && y < board.height;
}

void computePartitionBounds(int* startIdx, int* endIdx, int totalElements, int processRank, int totalProcesses) {
    int effectiveProcesses = totalProcesses - 1;

    int baseElementsPerProcess = totalElements / effectiveProcesses;
    int remainingElements = totalElements % effectiveProcesses;

    int workerIndex = processRank - 1;

    int hasExtraElement = (workerIndex < remainingElements) ? 1 : 0;
    *startIdx = workerIndex * baseElementsPerProcess + (workerIndex < remainingElements ? workerIndex : remainingElements);
    *endIdx = *startIdx + baseElementsPerProcess + hasExtraElement - 1;
}
