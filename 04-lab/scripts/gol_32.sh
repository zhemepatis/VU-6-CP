#!/bin/bash
#SBATCH -p main
#SBATCH -n33

WIDTH=32
THREAD_NUM=33

mpirun -np $THREAD_NUM ./main $WIDTH 1000 0
echo "---"
mpirun -np $THREAD_NUM ./main $WIDTH 10000 0
echo "---"
mpirun -np $THREAD_NUM ./main $WIDTH 100000 0