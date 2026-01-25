package edu.depaul.wberthou.search;

import edu.depaul.wberthou.graph.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * @see <a href="https://en.wikipedia.org/wiki/Iterative_deepening_depth-first_search">Wikipedia:Iterative Deepening Depth-First Search</a>
 * @see <a href="https://www.geeksforgeeks.org/dsa/iterative-deepening-searchids-iterative-deepening-depth-first-searchiddfs/">Geeks for Geeks:Iterative Deepening Depth-First Search</a>
 */
public class IterativeDeepeningDFS {
    private int depth;
    private Node target;

    public IterativeDeepeningDFS() {
        this.depth = Integer.MAX_VALUE;
    }

    public IterativeDeepeningDFS setMaxSearchDepth(int maxSearchDepth) {
        this.depth = maxSearchDepth;
        return this;
    }

    public void setTarget(Node target) {
        this.target = target;
    }

    public List<Node> iterativeDeepeningDepthFirstSearch(Node root) {
        List<Node> solutionPath = new ArrayList<>();

        for (int i = 0; i < depth; i++) {
            InternalSolution solution = depthLimitedSearch(root, target, depth, solutionPath);

            if (solution.foundTarget()) {
                System.out.println("Found target! Target: " + target.getName());
                return solutionPath;
            } else if (!solution.hasRemaining()) {
                solutionPath.clear();
                return List.of();
            }
            // else continue
        }

        return null;
    }

    private InternalSolution depthLimitedSearch(Node node, Node target, int depth, List<Node> path) {
        System.out.println("Visiting node " + node.getName());
        path.add(node);
        if (isGoal(node, target)) {
            return new InternalSolution(node, true);
        }

        if (depth <= 0) {
            return new InternalSolution(null, false);
        }

        boolean anyRemaining = false;

        for (Node child : node.getChildren()) {
            InternalSolution solution = depthLimitedSearch(child, target, depth - 1, path);

            if (solution.foundTarget()) {
                return new InternalSolution(solution.found, true);
            } else {
                path.removeLast();
            }
            if (solution.hasRemaining()) {
                anyRemaining = true;
            }
        }

        return new InternalSolution(null, anyRemaining);
    }

    private boolean isGoal(Node candidate, Node target) {
        return candidate.getName().equalsIgnoreCase(target.getName());
    }

    private record InternalSolution(Node found, boolean remaining) {
        public boolean foundTarget() {
            return found != null;
        }

        public boolean hasRemaining() {
            return remaining;
        }
    }

}
