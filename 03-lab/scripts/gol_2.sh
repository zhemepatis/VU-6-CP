#!/bin/bash
#SBATCH -p main
#SBATCH -n2

THREAD_NUM=2

java -cp ./bin GameOfLife 16 1000 $THREAD_NUM 0
echo "---"
java -cp ./bin GameOfLife 16 10000 $THREAD_NUM 0
echo "---"
java -cp ./bin GameOfLife 16 100000 $THREAD_NUM 0