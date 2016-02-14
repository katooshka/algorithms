import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

import java.util.Arrays;

public class PercolationStats {
    private int t;
    private double[] thresholds;

    public PercolationStats(int n, int t) {
        if (n < 0 || t < 0) {
            throw new IllegalArgumentException();
        }
        this.t = t;
        thresholds = new double[t];
        for (int k = 0; k < t; k++) {
            Percolation percolationGrid = new Percolation(n);
            int openSites = 0;
            while (!percolationGrid.percolates()) {
                int i = StdRandom.uniform(1, n + 1);
                int j = StdRandom.uniform(1, n + 1);
                if (!percolationGrid.isOpen(i, j)) {
                    percolationGrid.open(i, j);
                    openSites += 1;
                }
            }
            thresholds[k] = openSites * 1. / (n * n);
        }
    }

    public static void main(String[] args) {
        PercolationStats percolationStats = new PercolationStats(100, 10000);
        System.out.println(Arrays.toString(percolationStats.thresholds));
        System.out.println(percolationStats.mean());
        System.out.println(percolationStats.stddev());
        System.out.println(percolationStats.confidenceLo());
        System.out.println(percolationStats.confidenceHi());
    }

    public double mean() {
        return StdStats.mean(thresholds);
    }

    public double stddev() {
        return StdStats.stddev(thresholds);
    }

    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(t);
    }

    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(t);
    }
}
