#!/bin/bash
#SBATCH -p main
#SBATCH -n5

# Variables
CC=mpicc
CFLAGS="-Wall -Iheaders"
OBJ="main.o board.o calculation-task.o task-manager.o"
TARGET="main"

module --ignore_cache load "openmpi"

# Compile source files into object files
echo "Compiling source files..."
$CC $CFLAGS -c main.c -o main.o
$CC $CFLAGS -c board.c -o board.o
$CC $CFLAGS -c calculation-task.c -o calculation-task.o
$CC $CFLAGS -c task-manager.c -o task-manager.o

# Link object files to create executable
echo "Linking object files..."
$CC -o $TARGET $OBJ

echo "Build complete: $TARGET"

# Run tests
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