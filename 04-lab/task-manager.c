#include <stdio.h>
#include <mpi.h>
#include "headers/tags.h"
#include "headers/task-manager.h"

void taskManager() {
	int	ntasks, rank, work = 0;
	double result;
	MPI_Status status;

	MPI_Comm_size(MPI_COMM_WORLD, &ntasks);

	for (rank = 1; rank < ntasks; ++rank) 
	{
		work ++;
		
		MPI_Send(&work, 1, MPI_INT, rank, WORKTAG, MPI_COMM_WORLD);

		printf("Master(1): Procesui %d issiustas duomuo %d\n", rank, work);
	}

 	work++;
	while (work < 100) {
		MPI_Recv(&result, 1, MPI_DOUBLE, MPI_ANY_SOURCE, MPI_ANY_TAG, MPI_COMM_WORLD, &status);
		
		printf("Master(2): Is proceso %d gautas duomuo %f\n", status.MPI_SOURCE, result);

		MPI_Send(&work, 1, MPI_INT, status.MPI_SOURCE, WORKTAG, MPI_COMM_WORLD);

		printf("Master(3): Procesui %d issiustas duomuo %d\n", status.MPI_SOURCE, work);

		work++;
	}

	for (rank = 1; rank < ntasks; ++rank) {
		MPI_Recv(&result, 1, MPI_DOUBLE, MPI_ANY_SOURCE, MPI_ANY_TAG, MPI_COMM_WORLD, &status);

		printf("Master(4): Is proceso %d gautas duomuo %f\n", status.MPI_SOURCE, result);
	}

	for (rank = 1; rank < ntasks; ++rank) {
		MPI_Send(0, 0, MPI_INT, rank, DIETAG, MPI_COMM_WORLD);
		printf("Master(5): sustabdyti procesa %d\n", rank);
	}
}