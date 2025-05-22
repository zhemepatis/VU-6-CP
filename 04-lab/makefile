CC = mpicc
CFLAGS = -Wall -Iheaders
OBJ = main.o
TARGET = main.out

all: $(TARGET)

$(TARGET): $(OBJ)
	$(CC) -o $@ $(OBJ)

main.o: main.c 
	$(CC) $(CFLAGS) -c main.c

clean:
	rm -f *.o $(TARGET)
