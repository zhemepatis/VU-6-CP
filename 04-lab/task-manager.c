#include <stdio.h>
#include <stdlib.h>
#include <mpi.h>
#include "headers/tags.h"
#include "headers/task-manager.h"
#include "headers/board.h"

void applyResult(int* result, int start, int end, Board board);

void taskManager(Board board, int totalIterations, int verbose) {
	// MPI variables
	MPI_Status status;
	int totalProcesses;

	MPI_Comm_size(MPI_COMM_WORLD, &totalProcesses);

	if (verbose) {
		printf("ITERATION 0\n");
		printBoard(board);

		// pause until key is entered
		getchar();
	}

	// start calculations
	for (int iteration = 0; iteration < totalIterations; ++iteration) {
		// broadcast current state of board
		for (int rank = 1; rank < totalProcesses; ++rank) 
		{		
			MPI_Send(board.cells, board.width * board.height, MPI_INT, rank, CURR_STATE_INIT_TAG, MPI_COMM_WORLD);
		}

		// receive results
		for (int rank = 1; rank < totalProcesses; ++rank) {
			int start, end;
			computePartitionBounds(&start, &end, board.width * board.height, rank, totalProcesses);

			int* result = malloc((end - start + 1) * sizeof(int));
			MPI_Recv(result, end - start + 1, MPI_INT, rank, RESULTS_TAG, MPI_COMM_WORLD, &status);

			applyResult(result, start, end, board);
			free(result);
		}

		if (verbose) {
			printf("ITERATION %d\n", iteration + 1);
			printBoard(board);

			// pause until key is entered
			getchar();
		}
	}

	// kill workers
	for (int rank = 1; rank < totalProcesses; ++rank) 
	{		
		MPI_Send(0, 0, MPI_INT, rank, DIE_TAG, MPI_COMM_WORLD);
	}
}

void applyResult(int* result, int start, int end, Board board) {
	for (int i = start; i <= end; ++i) {
		board.cells[i] = result[i - start];
	}
}

