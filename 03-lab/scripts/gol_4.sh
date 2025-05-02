#!/bin/bash
#SBATCH -p main
#SBATCH -n4
java -cp ./bin GameOfLife 16 1000 4 0 > data/output/gol_4_1k.txt
java -cp ./bin GameOfLife 16 10000 4 0 > data/output/gol_4_10k.txt
java -cp ./bin GameOfLife 16 100000 4 0 > data/output/gol_4_100k.txt
java -cp ./bin GameOfLife 16 1000000 4 0 > data/output/gol_4_1m.txt