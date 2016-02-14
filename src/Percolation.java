import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Author: katooshka
 * Date: 1/29/16.
 */
public class Percolation {
    private final int n;
    private final int topIndex;
    private final int bottomIndex;
    private final boolean[][] cells;
    private final WeightedQuickUnionUF unionSet;

    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();
        this.n = n;
        cells = new boolean[n][n];
        unionSet = new WeightedQuickUnionUF(n * n + 2);
        topIndex = n * n;
        bottomIndex = n * n + 1;
    }

    public static void main(String[] args) {
        Percolation percolation = new Percolation(5);
        System.out.println(percolation.index(1, 1));
        System.out.println(percolation.index(1, 5));
        System.out.println(percolation.index(2, 1));
        System.out.println(percolation.index(5, 5));
        percolation.open(1, 2);
        percolation.open(2, 2);
        percolation.open(3, 2);
        percolation.open(3, 3);
        percolation.open(4, 3);
        System.out.println(percolation.percolates());
        percolation.open(5, 3);
        System.out.println(percolation.percolates());
    }

    public boolean isOpen(int i, int j) {
        checkRange(i, j);
        return cells[i - 1][j - 1];
    }

    public void open(int i, int j) {
        checkRange(i, j);
        if (isOpen(i, j)) return;
        cells[i - 1][j - 1] = true;
        if (i == 1) {
            unionSet.union(index(i, j), topIndex);
        }
        if (i == n) {
            unionSet.union(index(i, j), bottomIndex);
        }
        connectSites(i, j, i + 1, j);
        connectSites(i, j, i - 1, j);
        connectSites(i, j, i, j + 1);
        connectSites(i, j, i, j - 1);
    }

    private void connectSites(int i1, int j1, int i2, int j2) {
        if (isInRange(i2, j2) && isOpen(i2, j2)) {
            unionSet.union(index(i1, j1), index(i2, j2));
        }
    }

    public boolean isFull(int i, int j) {
        checkRange(i, j);
        return unionSet.connected(index(i, j), topIndex);
    }

    public boolean percolates() {
        return unionSet.connected(bottomIndex, topIndex);
    }

    private void checkRange(int i, int j) {
        if (!isInRange(i, j)) throw new IndexOutOfBoundsException();
    }

    private boolean isInRange(int i, int j) {
        return i >= 1 && j >= 1 && i <= n && j <= n;
    }

    private int index(int i, int j) {
        checkRange(i, j);
        return (i - 1) * n + (j - 1);
    }
}
