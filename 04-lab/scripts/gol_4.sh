#!/bin/bash
#SBATCH -p main
#SBATCH -n5

WIDTH=32
THREAD_NUM=5

module load openmpi
mpirun -np $THREAD_NUM ./main $WIDTH 1000 0
echo "---"

module load openmpi
mpirun -np $THREAD_NUM ./main $WIDTH 10000 0
echo "---"

module load openmpi
mpirun -np $THREAD_NUM ./main $WIDTH 100000 0