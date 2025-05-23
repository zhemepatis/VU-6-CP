#!/bin/bash
#SBATCH -p main
#SBATCH -n4

module load openmpi

mpicc -o connectivity tests/connectivity.c
mpirun ./connectivity