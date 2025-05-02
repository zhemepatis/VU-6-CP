#!/bin/bash
#SBATCH -p main
#SBATCH -n1

THREAD_NUM=1

java -cp ./bin GameOfLife 16 1000 $THREAD_NUM 0
echo "---"
java -cp ./bin GameOfLife 16 10000 $THREAD_NUM 0
echo "---"
java -cp ./bin GameOfLife 16 100000 $THREAD_NUM 0
echo "---"
java -cp ./bin GameOfLife 16 1000000 $THREAD_NUM 0