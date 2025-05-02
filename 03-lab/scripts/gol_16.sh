#!/bin/bash
#SBATCH -p main
#SBATCH -n16
java -cp ./bin GameOfLife 16 1000 16 0 > data/output/gol_16_1k.txt
java -cp ./bin GameOfLife 16 10000 16 0 > data/output/gol_16_10k.txt
java -cp ./bin GameOfLife 16 100000 16 0 > data/output/gol_16_100k.txt
java -cp ./bin GameOfLife 16 1000000 16 0 > data/output/gol_16_1m.txt