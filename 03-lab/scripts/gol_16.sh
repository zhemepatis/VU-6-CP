#!/bin/bash
#SBATCH -p main
#SBATCH -n16
java GameOfLife 16 1000 16 0 > gol_16_1k.txt
java GameOfLife 16 10000 16 0 > gol_16_10k.txt
java GameOfLife 16 100000 16 0 > gol_16_100k.txt
java GameOfLife 16 1000000 16 0 > gol_16_1m.txt