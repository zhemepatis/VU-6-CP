#!/bin/bash
#SBATCH -p main
#SBATCH -n8

CC=mpicc
CFLAGS="-Wall -Iheaders"
TARGET="main"

module load "openmpi"
$CC $CFLAGS -c main.c -o main.o

WIDTH=256
THREAD_NUM=8

mpirun -np $THREAD_NUM ./main $WIDTH 1000 0
echo "---"
mpirun -np $THREAD_NUM ./main $WIDTH 10000 0
echo "---"
mpirun -np $THREAD_NUM ./main $WIDTH 100000 0