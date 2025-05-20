#include <stdio.h>
#include <stdlib.h>
#include <mpi.h>
#include "headers/tags.h"
#include "headers/calculation-task.h"
#include "headers/board.h"

void process(Board board, int start, int end, int* nextState);

void calculationTask(int start, int end, int width, int height)
{
	MPI_Status status;

	Board board;
	int* cells = malloc(width * height * sizeof(int));
	int* result = malloc((end - start + 1) * sizeof(int));

	// initialise board
	board.width = width;
	board.height = height;
	board.cells = cells;

	// start calculations
	for(;;) {
		// receive current state of the board
		MPI_Recv(board.cells, width * height, MPI_INT, 0, MPI_ANY_TAG, MPI_COMM_WORLD, &status);

		if (status.MPI_TAG == DIE_TAG) {
			break;
		}
		else {
			process(board, start, end, result);
			MPI_Send(result, end - start + 1, MPI_INT, 0, RESULTS_TAG, MPI_COMM_WORLD);

			// TODO: add barrier ? 
		}
	}

	// free resources
	free(cells);
	free(result);
}

void process(Board board, int start, int end, int* nextState) {
	for(int i = start; i <= end; ++i) {
		int x = i % board.width;
		int y = i / board.width;

		int activeNeighbors = countActiveNeighbors(x, y, board);
		int currState = board.cells[i];

		int nextCellState = getNextCellState(currState, activeNeighbors);
		nextState[i - start] = nextCellState;
	}
}