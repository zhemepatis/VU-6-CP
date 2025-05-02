#!/bin/bash
#SBATCH -p main
#SBATCH -n8

OUTPUT=./data/output/gol_8_thread.txt
THREAD_NUM=8

java -cp ./bin GameOfLife 16 1000 $THREAD_NUM 0 >> $OUTPUT
echo "---" >> $OUTPUT
java -cp ./bin GameOfLife 16 10000 $THREAD_NUM 0 >> $OUTPUT
echo "---" >> $OUTPUT
java -cp ./bin GameOfLife 16 100000 $THREAD_NUM 0 >> $OUTPUT
echo "---" >> $OUTPUT
java -cp ./bin GameOfLife 16 1000000 $THREAD_NUM 0 >> $OUTPUT