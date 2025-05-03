#!/bin/bash
#SBATCH -p main
#SBATCH -n8

WIDTH=32
THREAD_NUM=8

java -cp ./bin GameOfLife $WIDTH 1000 $THREAD_NUM 0
echo "---"
java -cp ./bin GameOfLife $WIDTH 10000 $THREAD_NUM 0
echo "---"
java -cp ./bin GameOfLife $WIDTH 100000 $THREAD_NUM 0