import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {

	private static final double CONF95 = 1.96;

	private double[] result;
	private int n, t;
	private double mean, stddev;

	// test client (described below)
	public static void main(String[] args) {
		if (args.length != 2) {
			throw new IllegalArgumentException();
		}
		int n = Integer.parseInt(args[0]), t = Integer.parseInt(args[1]);
		PercolationStats percolationStats = new PercolationStats(n, t);
		StdOut.printf("%-23s = %s\n", "mean", percolationStats.mean());
		StdOut.printf("%-23s = %s\n", "stddev", percolationStats.stddev());
		StdOut.printf("%-23s = %s, %s\n", "95% confidence interval", percolationStats.confidenceLo(), percolationStats.confidenceHi());

	}
	// perform trials independent experiments on an n-by-n grid
	public PercolationStats(int n, int trials) {
		this.n = n;
		this.t = trials;
		if (n <= 0 || trials <= 0) {
			throw new IllegalArgumentException();
		}

		result = new double[trials];
		for (int i = 0; i < trials; i++) {
			result[i] = calculatePercolationThreshold(n);
		}

	}

	// sample mean of percolation threshold
	public double mean() {
        if (mean == 0)
		    mean = StdStats.mean(result);
		return mean;
	}

	// sample standard deviation of percolation threshold
	public double stddev() {
        if (stddev == 0)
		    stddev = StdStats.stddev(result);
		return stddev;
	}

	// low  endpoint of 95% confidence interval
	public double confidenceLo() {
		return mean() - CONF95 * stddev() / Math.sqrt(t);
	}

	// high endpoint of 95% confidence interval
	public double confidenceHi() {
		return mean() + CONF95 * stddev() / Math.sqrt(t);
	}

	private double calculatePercolationThreshold(int n) {
		Percolation percolation = new Percolation(n);
		while (!percolation.percolates()) {
			percolation.open(StdRandom.uniform(1, n + 1), StdRandom.uniform(1, n + 1));
		}
		return (double)percolation.numberOfOpenSites() / (n * n);
	}

}