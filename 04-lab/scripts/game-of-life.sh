#!/bin/bash
#SBATCH -p main
#SBATCH -n256

CC=mpicc
CFLAGS="-Wall -Iheaders"
TARGET="main"

module load "openmpi"
$CC $CFLAGS -c main.c -o main.o

WIDTH=256

for THREAD_NUM in 1 2 4 8 16 32 64 128 256; do
    echo "Running with THREAD_NUM=$THREAD_NUM"
    mpirun -np $THREAD_NUM ./main $WIDTH 1000 0
    echo "---"
    mpirun -np $THREAD_NUM ./main $WIDTH 10000 0
    echo "---"
    mpirun -np $THREAD_NUM ./main $WIDTH 100000 0
    echo "=========="
done
