#!/bin/bash
#SBATCH -p main
#SBATCH -n1
java GameOfLife 16 1000 1 0 > gol_1_1k.txt
java GameOfLife 16 10000 1 0 > gol_1_10k.txt
java GameOfLife 16 100000 1 0 > gol_1_100k.txt
java GameOfLife 16 1000000 1 0 > gol_1_1m.txt