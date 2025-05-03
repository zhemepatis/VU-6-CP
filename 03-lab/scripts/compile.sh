#!/bin/bash
javac -sourcepath ./src -d ./bin ./src/GameOfLife.java
javac -sourcepath ./tests ./tests/TTest.java
javac -sourcepath ./tests ./tests/TTest2.java