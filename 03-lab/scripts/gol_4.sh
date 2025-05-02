#!/bin/bash
#SBATCH -p main
#SBATCH -n4
java GameOfLife 16 1000 4 0 > gol_4_1k.txt
java GameOfLife 16 10000 4 0 > gol_4_10k.txt
java GameOfLife 16 100000 4 0 > gol_4_100k.txt
java GameOfLife 16 1000000 4 0 > gol_4_1m.txt