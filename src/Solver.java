import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Author: katooshka
 * Date: 2/26/16.
 */
public class Solver {
    private final Board initialBoard;
    private boolean solved = false;
    private boolean isSolvable;
    private int movementsNumber;
    private List<Board> solution;

    public Solver(Board initial) {
        if (initial == null) throw new NullPointerException();
        this.initialBoard = initial;
    }

    private static class SearchNode implements Comparable<SearchNode> {
        private Board board;
        private int stepsFromStart;
        private int distanceToFinish;
        private SearchNode previousNode;

        public SearchNode(Board board, int stepsFromStart, int distanceToFinish, SearchNode previousNode) {
            this.board = board;
            this.stepsFromStart = stepsFromStart;
            this.distanceToFinish = distanceToFinish;
            this.previousNode = previousNode;
        }
        
        public int getPriority() {
            return stepsFromStart + distanceToFinish;
        }
        
        @Override
        public int compareTo(SearchNode o) {
            return Integer.compare(this.getPriority(), o.getPriority());
        }
    }

    public boolean isSolvable() {
        solveIfNeeded();
        return isSolvable;
    }

    public int moves() {
        solveIfNeeded();
        return movementsNumber;
    }

    public Iterable<Board> solution() {
        solveIfNeeded();
        return solution;
    }

    private void solveIfNeeded() {
        if (solved) return;
        MinPQ<SearchNode> queue = new MinPQ<SearchNode>();
        MinPQ<SearchNode> twinQueue = new MinPQ<SearchNode>();
        queue.insert(new SearchNode(initialBoard, 0, initialBoard.manhattan(), null));
        twinQueue.insert(new SearchNode(initialBoard.twin(), 0, initialBoard.twin().manhattan(), null));
        
        while (!queue.min().board.isGoal() && !twinQueue.min().board.isGoal()) {
            insertNeighbors(queue, queue.delMin());
            insertNeighbors(twinQueue, twinQueue.delMin());
        }
        
        if (queue.min().board.isGoal()) {
            solution = unwindPath(queue.min());
            isSolvable = true;
            movementsNumber = solution.size() - 1;
            solved = true;
        } else {
            isSolvable = false;
            movementsNumber = -1;
            solution = null;
            solved = true;
        }                
    }

    private List<Board> unwindPath(SearchNode finishNode) {
        List<Board> result = new ArrayList<Board>();
        while (finishNode.previousNode != null) {
            result.add(finishNode.board);
            finishNode = finishNode.previousNode;
        }
        result.add(finishNode.board);
        Collections.reverse(result);
        return result;
    }

    private void insertNeighbors(MinPQ<SearchNode> queue, SearchNode currentNode) {
        for (Board neighbor : currentNode.board.neighbors()) {
            if (currentNode.previousNode == null || !currentNode.previousNode.board.equals(neighbor)) {
                int stepsFromStart = currentNode.stepsFromStart + 1;
                int distanceToFinish = neighbor.manhattan();
                queue.insert(new SearchNode(neighbor, stepsFromStart, distanceToFinish, currentNode));
            }
        }
    }
}


