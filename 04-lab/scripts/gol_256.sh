#!/bin/bash
#SBATCH -p main
#SBATCH -n257

WIDTH=32
THREAD_NUM=257

mpirun -np $THREAD_NUM ./main $WIDTH 1000 0
echo "---"
mpirun -np $THREAD_NUM ./main $WIDTH 10000 0
echo "---"
mpirun -np $THREAD_NUM ./main $WIDTH 100000 0