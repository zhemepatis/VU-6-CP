#!/bin/bash
#SBATCH -p main
#SBATCH -n4

# compile arguments
CC=mpicc
CFLAGS="-Wall -Iheaders"
TARGET="main"

# building project
module load "openmpi"
$CC $CFLAGS -c main.c -o main.o

# experiment arguments
WIDTH=256
THREAD_NUM=4

# run experiment
mpirun -np $THREAD_NUM ./main $WIDTH 100 0
echo "---"
mpirun -np $THREAD_NUM ./main $WIDTH 1000 0
echo "---"
mpirun -np $THREAD_NUM ./main $WIDTH 10000 0