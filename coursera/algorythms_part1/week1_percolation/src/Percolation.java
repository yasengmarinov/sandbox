import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

	private static final int OPEN = 1;

	private int[][] grid;
	private int openedSites;
	private int quickUnionBeginRoot, quickUnionEndRoot;
	private WeightedQuickUnionUF quickUnionUF;

	// test client (optional)
	public static void main(String[] args) throws Exception{
	}
	// create n-by-n grid, with all sites blocked
	public Percolation(int n) throws IllegalArgumentException{
		if (n <= 0) {
			throw new IllegalArgumentException();
		}
		grid = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				grid[i][j] = 0;
			}
		}
		quickUnionUF = new WeightedQuickUnionUF(n * n + 2);
		quickUnionBeginRoot = n * n;
		quickUnionEndRoot = n * n + 1;
		for (int i = 1; i <=n ; i++) {
			quickUnionUF.union(quickUnionBeginRoot, coordsToConnectionIndex(1, i));
			quickUnionUF.union(quickUnionEndRoot, coordsToConnectionIndex(n, i));
		}

		openedSites = 0;
	}

	// open site (row, col) if it is not open already
	public void open(int row, int col) {
		validateCoordinates(row, col);
		if (grid[row -1][col - 1] != OPEN) {
			grid[row - 1][col - 1] = OPEN;
			openSideConnections(row, col);
			openedSites++;
		}
	}

	// is site (row, col) open?
	public boolean isOpen(int row, int col) {
		return grid[row - 1][col - 1] == OPEN;
	}

	// is site (row, col) full?
	public boolean isFull(int row, int col) {
		return isOpen(row, col) && quickUnionUF.connected(quickUnionBeginRoot, coordsToConnectionIndex(row, col));
	}

	// number of open sites
	public int numberOfOpenSites() {
		return openedSites;
	}
	// does the system percolate?
	public boolean percolates() {
		return quickUnionUF.connected(quickUnionBeginRoot, quickUnionEndRoot);
	}

	private void validateCoordinates(int row, int col) throws IndexOutOfBoundsException{
		if (!isValidCoordinate(row) || !isValidCoordinate(col)) {
			throw new IndexOutOfBoundsException();
		}
	}

	private boolean isValidCoordinate(int coord) {
		return coord > 0 && coord <= grid.length;
	}

	private void openSideConnections(int row, int col) {
		openConnection(row, col, row - 1, col);
		openConnection(row, col, row, col - 1);
		openConnection(row, col, row, col + 1);
		openConnection(row, col, row + 1, col);
	}

	private void openConnection(int row, int col, int row2, int col2) {
		if (!isValidCoordinate(row2) || !isValidCoordinate(col2)) {
			return;
		}
		if (isOpen(row2, col2)) {
			quickUnionUF.union(coordsToConnectionIndex(row, col), coordsToConnectionIndex(row2, col2));
		}
	}

	private int coordsToConnectionIndex(int row, int col) {
		return (row - 1) * grid.length + col - 1;
	}
}