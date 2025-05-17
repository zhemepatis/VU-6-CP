#include <stdio.h>
#include <mpi.h>
#include "headers/tags.h"
#include "headers/calculation-task.h"

void calculationTask()
{
	double result;
	int work;
	MPI_Status status;
	
	for (;;) {
		MPI_Recv(&work, 1, MPI_INT, 0, MPI_ANY_TAG, MPI_COMM_WORLD, &status);

		if (status.MPI_TAG == DIETAG) {
			printf("Slave(1): atsiusta baigmes zyme\n");
			return;
		}
		else
		{
			printf("Slave(2): atsiustas duomuo %d\n", work);

			result = work * work;
			MPI_Send(&result, 1, MPI_DOUBLE, 0, 0, MPI_COMM_WORLD);

			printf("Slave(3): issiustas rezultatas %f\n", result);
		}
	}
}