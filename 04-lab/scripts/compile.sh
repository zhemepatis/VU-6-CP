#!/bin/bash

# compile arguments
CC=mpicc
CFLAGS="-Wall -Iheaders"
TARGET="main"

# building project
module load openmpi
$CC $CFLAGS -c main.c -o main.o  
$CC main.o -o $TARGET            