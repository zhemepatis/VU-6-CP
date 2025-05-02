#!/bin/bash
#SBATCH -p main
#SBATCH -n2
java GameOfLife 16 1000 2 0 > gol_2_1k.txt
java GameOfLife 16 10000 2 0 > gol_2_10k.txt
java GameOfLife 16 100000 2 0 > gol_2_100k.txt
java GameOfLife 16 1000000 2 0 > gol_2_1m.txt