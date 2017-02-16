/**
 * Created by b06514a on 2/2/2017.
 */

import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdRandom;

public class Board {

    private final int[][] blocks;

    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        int[][] tmpBlocks = new int[blocks.length][blocks.length];
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks.length; j++) {
                tmpBlocks[i][j] = blocks[i][j];
            }
        }
        this.blocks = tmpBlocks;
    }

    // board dimension n
    public int dimension() {
        return blocks.length;
    }
    // number of blocks out of place
    public int hamming() {
        int distance = 0;
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (blocks[i][j] != 0 && blocks[i][j] != j + i * dimension() + 1) {
                    distance++;
                }
            }
        }
        return distance;
    }
    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int distance = 0;
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (blocks[i][j] != 0 && blocks[i][j] != j + i * dimension() + 1) {
                    distance+= calculateManhattanDistance(blocks[i][j], i, j);
                }
            }
        }
        return distance;
    }

    private int calculateManhattanDistance(int square, int currentI, int currentJ) {
        int distance = 0;
        int desiredJ = square / dimension();
        int desiredI = square - desiredJ * dimension() - 1;
        distance+= (currentI >= desiredI)? currentI - desiredI : desiredI - currentI;
        distance+= (currentJ >= desiredJ)? currentJ - desiredJ : desiredJ - currentJ;
        return distance;
    }

    // is this board the goal board?
    public boolean isGoal() {
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (blocks[i][j] != 0 && blocks[i][j] != j + i * dimension() + 1) {
                    return false;
                }
            }
        }
        return true;
    }
    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        int randOne, randTwo;
        do {
            randOne = StdRandom.uniform(0, dimension() * dimension() + 1);
        } while (getNthBlock(randOne) != 0);

        do {
            randTwo = StdRandom.uniform(0, dimension() * dimension() + 1);
        } while (randTwo != randOne && getNthBlock(randTwo) != 0);
        Board twin = new Board(blocks);
        twin.exch(randOne, randTwo);
        return twin;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (this == y) return true;
        if (y == null) return false;
        if (this.getClass() != y.getClass()) return false;
        Board that = (Board) y;
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (this.blocks[i][j] != that.blocks[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Stack<Board> neighbours = new Stack<>();
        int emptyI = 0, emptyJ = 0;
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (blocks[i][j] == 0) {
                    emptyI = i;
                    emptyJ = j;
                }
            }
        }
        if (emptyI > 0) neighbours.push(exch(emptyI - 1, emptyJ, emptyI, emptyJ));
        if (emptyI < dimension() - 1) neighbours.push(exch(emptyI + 1, emptyJ, emptyI, emptyJ));
        if (emptyJ > 0) neighbours.push(exch(emptyI, emptyJ - 1, emptyI, emptyJ));
        if (emptyJ < dimension() - 1) neighbours.push(exch(emptyI, emptyJ + 1, emptyI, emptyJ));
        return neighbours;
    }
    // string representation of this board (in the output format specified below)
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(dimension() + "\n");
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                s.append(String.format("%2d ", blocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    // unit tests (not graded)
    public static void main(String[] args) {
        int[][] array = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        Board board = new Board(array);
//        System.out.println(board.toString());
        assert (board.hamming() == 0);
        assert (board.manhattan() == 0);
        assert (board.isGoal() == true);

        int[][] array2 = {{2, 1, 3}, {4, 5, 6}, {7, 8, 0}};
        Board board2 = new Board(array2);
        assert (board2.hamming() == 2);
        assert (board2.manhattan() == 2);
        assert (board2.isGoal() == false);

        int[][] array3 = {{1, 2, 3}, {4, 0, 5}, {6, 7, 8}};
        Board board3 = new Board(array3);
        System.out.println(board3.toString());
        for (Board neigh: board3.neighbors()) {
            System.out.println(neigh);
        }
    }

    private Board exch(int randOne, int randTwo) {
        int iOne, jOne, iTwo, jTwo;
        iOne = randOne / dimension();
        iTwo = randTwo / dimension();
        jOne = randOne - iOne * dimension() - 1;
        jTwo = randTwo - iTwo * dimension() - 1;

        return exch(iOne, jOne, iTwo, jTwo);
    }

    private Board exch(int iOne, int jOne, int iTwo, int jTwo) {
        Board clone = new Board(this.blocks);
        int tmp = clone.blocks[iOne][jOne];
        clone.blocks[iOne][jOne] = clone.blocks[iTwo][jTwo];
        clone.blocks[iTwo][jTwo] = tmp;
        return clone;
    }

    private int getNthBlock(int num) {
        int i = num / dimension();
        int j = num - i * dimension() - 1;
        return blocks[i][j];
    }
}