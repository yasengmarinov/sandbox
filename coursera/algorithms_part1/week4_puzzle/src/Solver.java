/**
 * Created by yasen on 2/19/17.
 */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

    private MinPQ<Node> gameTree;
    private Stack<Board> solution = new Stack<>();
    private boolean solvable;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        gameTree = new MinPQ<>();
        gameTree.insert(new Node(initial, null, 0));
        solve();
        if (isSolvable()) findSolution();
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return (solvable)? solution.size() - 1: -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if(!solvable) return null;
        return solution;
    }

    private void findSolution() {
        Node currNode = gameTree.min();
        solution.push(currNode.board);
        while (currNode.previousNode != null) {
            currNode = currNode.previousNode;
            solution.push(currNode.board);
        }
        gameTree = null;
    }

    private void solve() {
        MinPQ<Node> twinGameTree = new MinPQ<>();
        twinGameTree.insert(new Node(gameTree.min().board.twin(), null, 0));
        while (!gameTree.min().board.isGoal() && !twinGameTree.min().board.isGoal() ) {
            Node previousMin = gameTree.delMin();
            for (Board neighbour:previousMin.board.neighbors()) {
                if (previousMin.previousNode == null || !neighbour.equals(previousMin.previousNode.board)) {
                    gameTree.insert(new Node(neighbour, previousMin, previousMin.moves + 1));
                }
            }
            Node twinPreviousMin = twinGameTree.delMin();

            for (Board neighbour:twinPreviousMin.board.neighbors()) {
                if (twinPreviousMin.previousNode == null || !neighbour.equals(twinPreviousMin.previousNode.board)) {
                    twinGameTree.insert(new Node(neighbour, twinPreviousMin, twinPreviousMin.moves + 1));
                }
            }
        }
        if (gameTree.min().board.isGoal()) {
            solvable = true;
        } else {
            solvable = false;
        }
    }

    // solve a slider puzzle (given below)
    public static void main(String[] args) {
        int[][] array = {{1, 2, 4, 3}, {7, 15, 10, 9}, {8, 6, 11, 13}, {5, 14, 12, 0}};
        Board initial = new Board(array);
        Solver solver = new Solver(initial);
        System.out.println(solver.isSolvable());
        if (solver.isSolvable()) {
            for (Board board:solver.solution()) {
                System.out.println(board);
            }
            System.out.println(solver.moves());
        }

//         create initial board from file
//        In in = new In(args[0]);
//        int n = in.readInt();
//        int[][] blocks = new int[n][n];
//        for (int i = 0; i < n; i++)
//            for (int j = 0; j < n; j++)
//                blocks[i][j] = in.readInt();
//        Board initial = new Board(blocks);
//
//        // solve the puzzle
//        Solver solver = new Solver(initial);
//
//        // print solution to standard output
//        if (!solver.isSolvable())
//            StdOut.println("No solution possible");
//        else {
//            StdOut.println("Minimum number of moves = " + solver.moves());
//            for (Board board : solver.solution())
//                StdOut.println(board);
//        }
    }

    private class Node implements Comparable<Node>{
        private final Board board;
        private final Node previousNode;
        private final int moves;
        private final int manhattan;

        public Node(Board board, Node previousNode, int moves) {
            this.board = board;
            this.previousNode = previousNode;
            this.moves = moves;
            manhattan = board.manhattan();
        }

        @Override
        public int compareTo(Node o) {
            Node toCmp = o;
            return Integer.compare(manhattan + moves, toCmp.manhattan + toCmp.moves);
        }
    }
}