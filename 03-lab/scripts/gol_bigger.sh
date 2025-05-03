#!/bin/bash
#SBATCH -p main
#SBATCH -n32

WIDTH=32

java -cp ./bin GameOfLife $WIDTH 1000000 1 0
echo "---"
java -cp ./bin GameOfLife $WIDTH 1000000 2 0
echo "---"
java -cp ./bin GameOfLife $WIDTH 1000000 4 0
echo "---"
java -cp ./bin GameOfLife $WIDTH 1000000 8 0
echo "---"
java -cp ./bin GameOfLife $WIDTH 1000000 16 0
echo "---"
java -cp ./bin GameOfLife $WIDTH 1000000 32 0