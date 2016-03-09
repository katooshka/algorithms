import java.util.ArrayList;
import java.util.List;

/**
 * Author: katooshka
 * Date: 2/26/16.
 */
public class Board {
    private final int[][] blocks;
    private int n;
    private int emptyBlockX;
    private int emptyBlockY;

    public Board(int[][] blocks) {
        this.blocks = copyBoardBlocks(blocks);
        this.n = this.blocks.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (blocks[i][j] == 0) {
                    emptyBlockX = i;
                    emptyBlockY = j;
                }
            }
        }
    }

    public int dimension() {
        return n;
    }

    public int hamming() {
        int counter = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (blocks[i][j] != 0 && blocks[i][j] != i * n + j + 1) {
                    counter++;
                }
            }
        }
        return counter;
    }

    public int manhattan() {
        int counter = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (blocks[i][j] != 0) {
                    int endI = (blocks[i][j] - 1) / n;
                    int endJ = (blocks[i][j] - 1) % n;
                    counter = counter + Math.abs(endI - i) + Math.abs(endJ - j);
                }
            }
        }
        return counter;
    }

    public boolean isGoal() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                boolean lastCell = (i == n - 1) && (j == n - 1);
                int expected = lastCell ? 0 : i * n + j + 1;
                if (blocks[i][j] != expected) {
                    return false;
                }
            }
        }
        return true;
    }

    public Board twin() {
        return (blocks[0][0] == 0 || blocks[0][1] == 0) ? swap(1, 0, 1, 1) : swap(0, 0, 0, 1);
    }

    public boolean equals(Object object) {
        if (object == null) return false;
        if (this == object) return true;
        if (getClass() != object.getClass()) return false;
        Board otherBoard = (Board) object;
        if (otherBoard.n != n) return false;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (blocks[i][j] != otherBoard.blocks[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public Iterable<Board> neighbors() {
        List<Board> neighbors = new ArrayList<Board>();
        if (emptyBlockX > 0) {
            neighbors.add(swap(emptyBlockX - 1, emptyBlockY, emptyBlockX, emptyBlockY));
        }
        if (emptyBlockX < n - 1) {
            neighbors.add(swap(emptyBlockX + 1, emptyBlockY, emptyBlockX, emptyBlockY));
        }
        if (emptyBlockY > 0) {
            neighbors.add(swap(emptyBlockX, emptyBlockY - 1, emptyBlockX, emptyBlockY));
        }
        if (emptyBlockY < n - 1) {
            neighbors.add(swap(emptyBlockX, emptyBlockY + 1, emptyBlockX, emptyBlockY));
        }
        return neighbors;
    }


    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(n).append("\n");
        for (int[] row : blocks) {
            for (int cell : row) {
                sb.append("\t").append(cell);
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    private Board swap(int i1, int j1, int i2, int j2){
        int[][] array = copyBoardBlocks(this.blocks);
        int temp = array[i1][j1];
        array[i1][j1] = array[i2][j2];
        array[i2][j2] = temp;
        return new Board(array);
    }

    private int[][] copyBoardBlocks(int[][] blocks) {
        int[][] copy = new int[blocks.length][blocks.length];
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks.length; j++) {
                copy[i][j] = blocks[i][j];
            }
        }
        return copy;
    }
}

