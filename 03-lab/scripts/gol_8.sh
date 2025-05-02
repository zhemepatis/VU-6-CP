#!/bin/bash
#SBATCH -p main
#SBATCH -n8
java GameOfLife 16 1000 8 0 > gol_8_1k.txt
java GameOfLife 16 10000 8 0 > gol_8_10k.txt
java GameOfLife 16 100000 8 0 > gol_8_100k.txt
java GameOfLife 16 1000000 8 0 > gol_8_1m.txt