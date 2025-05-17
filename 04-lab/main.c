#include <stdio.h>
#include <mpi.h>
#include <stdlib.h>
#include <time.h>
#include "headers/task-manager.h"
#include "headers/calculation-task.h"

int** initBoard(int x, int y);
void printBoard(int** board, int x, int y);
void freeBoard(int** board, int x, int y);

int main(int argc, char* argv[]) {
	srand(time(NULL));

	int rank;

	MPI_Init(&argc, &argv);
	MPI_Comm_rank(MPI_COMM_WORLD, &rank);

	if (rank == 0) {
		int x = atoi(argv[1]);
		int y = atoi(argv[2]);

		int** board = initBoard(x, y);
		printBoard(board, x, y);

		taskManager();

		freeBoard(board, x, y);
	} 
	else {
		calculationTask();
	}

	MPI_Finalize();
}

int** initBoard(int x, int y) {
	int** board = malloc(y * sizeof(int*));

	for(int i = 0; i < y; ++i) {
		board[i] = malloc(x * sizeof(int));

		for(int j = 0; j < x; ++j) {
			board[i][j] = rand() % 2;
		}
	}

	return board;
}

void printBoard(int** board, int x, int y) {
	for(int i = 0; i < y; ++i) {
		for(int j = 0; j < x; ++j) {
			printf("%d ", board[i][j]);
		}

		printf("\n");
	}
}

void freeBoard(int** board, int x, int y) {
	for(int i = 0; i < y; ++i) {
		free(board[i]);
	}

	free(board);
}


