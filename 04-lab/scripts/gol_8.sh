#!/bin/bash
#SBATCH -p main
#SBATCH -n8

# compile arguments
CC=mpicc
CFLAGS="-Wall -Iheaders"
TARGET="main"

# build project
module load openmpi
$CC $CFLAGS -c main.c -o main.o  
$CC main.o -o $TARGET

# experiment arguments
WIDTH=256
THREAD_NUM=8

# run experiment
echo "Running with $THREAD_NUM thread(s)"

mpirun -np $THREAD_NUM ./main $WIDTH 100 0
echo "---"
mpirun -np $THREAD_NUM ./main $WIDTH 1000 0
echo "---"
mpirun -np $THREAD_NUM ./main $WIDTH 10000 0