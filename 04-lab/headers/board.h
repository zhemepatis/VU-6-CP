#ifndef BOARD_H
    #define BOARD_H

    struct Board {
        int width;
        int height;
        int* cells;
    };
    typedef struct Board Board;

    Board initBoard(int x, int y);
    void freeBoard(Board board);
    void printBoard(Board board);

    int countActiveNeighbors(int x, int y, Board board);
    int getNextCellState(int state, int activeNeighborCount);

    int isInBounds(int x, int y, Board board);
    void computePartitionBounds(int* startIdx, int* endIdx, int totalElements, int processRank, int totalProcesses);

#endif