/**
 * Created by b06514a on 2/2/2017.
 */
public class Board {

    private final int[][] blocks;

    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        this.blocks = blocks.clone();
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
                if (blocks[i][j] != 0 && blocks[i][j] != i + j * dimension() + 1) {
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
                if (blocks[i][j] != 0 && blocks[i][j] != i + j * dimension() + 1) {
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
                if (blocks[i][j] != 0 && blocks[i][j] != i + j * dimension() + 1) {
                    return false;
                }
            }
        }
        return true;
    }
    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {

    }
    // does this board equal y?
    public boolean equals(Object y) {

    }
    // all neighboring boards
    public Iterable<Board> neighbors() {

    }
    // string representation of this board (in the output format specified below)
    public String toString() {

    }

    // unit tests (not graded)
    public static void main(String[] args) {

    }
}