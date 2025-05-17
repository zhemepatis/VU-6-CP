#include <stdio.h>
#include <mpi.h>
#include "headers/task-manager.h"
#include "headers/calculation-task.h"

int main(int argc, char *argv[]) {
	int rank;

	MPI_Init(&argc, &argv);
	MPI_Comm_rank(MPI_COMM_WORLD, &rank);

	if (rank == 0) {
		taskManager();
	} 
	else {
		calculationTask();
	}

	MPI_Finalize();
}



