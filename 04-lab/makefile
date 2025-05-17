CC = mpicc
CFLAGS = 
DEPS = headers/tags.h headers/calculation-task.h headers/task-manager.h
OBJ = main.o calculation-task.o task-manager.o

%.o: %.c $(DEPS)
	$(CC) $(CFLAGS) -c -o $@ $<

main: $(OBJ)
	$(CC) $(CFLAGS) -o $@ $^

clean:
	rm -f *.o main