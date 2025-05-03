#!/bin/bash
#SBATCH -p main
#SBATCH -n32

java -cp ./tests TTest
java -cp ./tests TTest2