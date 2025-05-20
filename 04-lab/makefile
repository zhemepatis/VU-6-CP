CC = mpicc
CFLAGS = -Wall -Iheaders
OBJ = main.o board.o calculation-task.o task-manager.o
TARGET = main

all: $(TARGET)

$(TARGET): $(OBJ)
	$(CC) -o $@ $(OBJ)

main.o: main.c headers/board.h headers/calculation-task.h headers/task-manager.h
	$(CC) $(CFLAGS) -c main.c

board.o: board.c headers/board.h
	$(CC) $(CFLAGS) -c board.c

calculation-task.o: calculation-task.c headers/calculation-task.h headers/board.h
	$(CC) $(CFLAGS) -c calculation-task.c

task-manager.o: task-manager.c headers/task-manager.h headers/board.h
	$(CC) $(CFLAGS) -c task-manager.c

clean:
	rm -f *.o $(TARGET)
