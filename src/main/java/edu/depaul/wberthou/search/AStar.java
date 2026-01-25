package edu.depaul.wberthou.search;

import edu.depaul.wberthou.graph.Graph;
import edu.depaul.wberthou.graph.Node;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * This class was modified from an AI-generated response. See problem3-full-disclosure.md for complete explanation.
 */
public class AStar {
    private final Map<Node, Node> cameFrom = new HashMap<>();
    private final Map<Node, Integer> gScore = new HashMap<>();
    private final Map<Node, Integer> fScore = new HashMap<>();

    public AStar() {

    }

    public List<Node> aStarSearch(Graph graph, Node start, Node target) {
        /* Open set ordered by f(n) = g(n) + h(n) */
        PriorityQueue<Node> openSet = new PriorityQueue<>(
            Comparator.comparingDouble(n -> fScore.getOrDefault(n, Integer.MAX_VALUE))
        );

        /* Cost from start -> node */
        gScore.clear();
        gScore.put(start, 0);

        /* Estimated total cost */
        fScore.clear();
        fScore.put(start, heuristic(start, target));

        /* Parent map for path reconstruction */
        cameFrom.clear();

        openSet.add(start);

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();

            if (current.equals(target)) {
                return reconstructPath(current);
            }

            for (Node neighbor : current.getChildren()) {
                int tentativeG = gScore.get(current) + graph.cost(current, neighbor);

                if (tentativeG < gScore.getOrDefault(neighbor, Integer.MAX_VALUE)) {
                    cameFrom.put(neighbor, current);
                    gScore.put(neighbor, tentativeG);
                    fScore.put(neighbor, tentativeG + heuristic(neighbor, target));

                    if (!openSet.contains(neighbor)) {
                        openSet.add(neighbor);
                    }
                }
            }
        }

        return Collections.emptyList();
    }

    private int heuristic(Node a, Node b) {
        return (int) Node.distanceBetween(a, b);
    }

    private List<Node> reconstructPath(Node target) {
        LinkedList<Node> path = new LinkedList<>();
        Node current = target;

        while (current != null) {
            path.addFirst(current);
            current = cameFrom.get(current);
        }

        return path;
    }

}
