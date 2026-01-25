# Problem 3 Full Disclosure

## Problem 3: 20 Points: A* Search

Consider both the (X,Y) coordinates and the path costs between the locations in the
graph above. Write an A* search program to find the best path from Class Room
Building A to the Conference Center.

The answer to problem 3 was generated using AI:
ChatGPT GPT-5.2 (free version)
as of January 19, 2026

---

## Prompt

Given the following classes, implement an A* search algorithm:

<code>
    package edu.depaul.wberthou.graph;
    
    import edu.depaul.wberthou.util.MathUtils;
    
    import java.util.ArrayList;
    import java.util.List;
    import java.util.Objects;
    
    public class Node {
        protected String name;
        protected int x;
        protected int y;
        protected List<Node> children;
    
        public Node() {
            this.children = new ArrayList<>(1);
        }
    
        public Node(String name, int x, int y) {
            this(name, x, y, null);
        }
    
        public Node(String name, int x, int y, List<Node> children) {
            this.name = name;
            this.x = x;
            this.y = y;
            this.children = Objects.requireNonNullElseGet(children, ArrayList::new);
        }

        // Getters and Setters have been omitted for brevity //

        public void addChild(Node child) {
            this.children.add(child);
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (!(object instanceof Node node)) {
                return false;
            }
            return x == node.x && y == node.y && name.equals(node.name);
        }
    
        @Override
        public int hashCode() {
            return 219 + Objects.hash(name, x, y);
        }
    
        @Override
        public String toString() {
            String childList = collectChildren();
    
            return String.format("Node [\"%s\", Coordinates (%3d, %3d), Connections: %s]", name, x, y, childList);
        }
    
        private String collectChildren() {
            if (children.isEmpty()) {
                return "NONE";
            }
    
            StringBuilder builder = new StringBuilder();
    
            for (int i = 0; i < children.size(); i++) {
                builder.append(children.get(i).name);
                if (i < children.size() - 1) {
                    builder.append(',');
                }
            }
    
            return builder.toString();
        }
    
        public static double distanceBetween(Node first, Node second) {
            return MathUtils.distance(first.x, first.y, second.x, second.y);
        }
    
    }
    
    public class Graph {
    private final FileReader reader;
    private List<Node> nodes;
    /** Weighted edges **/
    private List<Arc> arcs;
    
        public Graph(String jsonFileName) {
            this.reader = new FileReader();
            init(jsonFileName);
        }
    
        private void init(String filename) {
            FileReader.ResultPair<Node, Arc> result;
            try {
                result = reader.readFileToJavaObjects(filename);
                buildGraph(result);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    
        private void buildGraph(FileReader.ResultPair<Node, Arc> data) {
            this.nodes = data.result1();
            this.arcs = data.result2();
            connectNodes(arcs);
        }
    
        private void connectNodes(List<Arc> weightedEdges) {
            for (Arc arc : weightedEdges) {
                arc.first.addChild(arc.second);
            }
        }

        public Optional<Node> get(String name) {
            return nodes.stream()
                .filter((n) -> n.name.equals(name))
                .findFirst();
        }
    
        public Optional<Arc> getArc(Node source, Node destination) {
            for (Arc arc : arcs) {
                Arc temp = new Arc(source, destination);
                if (arc.weaklyEquals(temp)) {
                    return Optional.of(arc);
                }
            }
            return Optional.empty();
        }
    
        public boolean isDirectlyConnectedTo(Node source, Node destination) {
            for (Arc arc : arcs) {
                Arc temp = new Arc(source, destination);
                if (arc.weaklyEquals(temp)) {
                    return true;
                }
            }
            return false;
        }
    
        public List<Arc> getArcs() {
            return arcs;
        }
    
        public int size() {
            return nodes.size();
        }
    
        /**
         * For <code>Nodes</code> that are connected together, assume the given path lengths are correct.<br>
         * For <code>Nodes</code> that are not connected, use Euclidean distance.
         *
         * @param source        first Node
         * @param destination   second Node
         * @return              the distance (as an Integer)
         */
        public int cost(Node source, Node destination) {
            if (isDirectlyConnectedTo(source, destination)) {
                Arc arc = getArc(source, destination).orElseThrow();
                return arc.getDistance();
            }
            return (int) MathUtils.distance(source.x, source.y, destination.x, destination.y);
        }
    
    }
    
    public class Arc {
        /** Source **/
        protected Node first;
        /** Destination **/
        protected Node second;
        /** Weight **/
        protected int distance;
    
        public Arc() {
    
        }
    
        public Arc(Node first, Node second) {
            this(first, second, 0);
        }
    
        public Arc(Node first, Node second, int distance) {
            this.first = first;
            this.second = second;
            this.distance = distance;
        }
    
        // Getters and Setters have been omitted for brevity //
    
        public boolean weaklyEquals(Arc other) {
            return this.first.equals(other.first) && this.second.equals(other.second);
        }
    
        @Override
        public String toString() {
            return "Arc{" +
                    "first_node=" + first +
                    ", second_node=" + second +
                    ", distance=" + distance +
                    '}';
        }

    }
</code>
---

## Corresponding Classes

See **AStar.java** for the resulting, edited code.
The exact output was as follows:

<code>
    package edu.depaul.wberthou.graph.search;
    import edu.depaul.wberthou.graph.Graph;
    import edu.depaul.wberthou.graph.Node;
    import java.util.*;
    
    /**
     * A* pathfinding over Graph / Node / Arc
     */
    public class AStarSearch {
        /**
         * Finds the shortest path from start to goal using A*
         *
         * @param graph Graph containing nodes and weighted arcs
         * @param start Start node
         * @param goal  Goal node
         * @return Ordered list of nodes representing the path (empty if no path)
         */
        public static List<Node> findPath(Graph graph, Node start, Node goal) {
    
            /* Open set ordered by f(n) = g(n) + h(n) */
            PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingDouble(n -> fScore.getOrDefault(n, Double.POSITIVE_INFINITY)));
            
            /* Cost from start → node */
            gScore.clear();
            gScore.put(start, 0.0);
            
            /* Estimated total cost */
            fScore.clear();
            fScore.put(start, heuristic(start, goal));
            
            /* Parent map for path reconstruction */
            cameFrom.clear();
            
            openSet.add(start);
            
            while (!openSet.isEmpty()) {
                Node current = openSet.poll();
        
                if (current.equals(goal)) {
                   return reconstructPath(current);
                }
            
                for (Node neighbor : current.getChildren()) {
                    double tentativeG = 
                        gScore.get(current) + graph.cost(current, neighbor);
            
                    if (tentativeG < gScore.getOrDefault(neighbor, Double.POSITIVE_INFINITY)) {
                        cameFrom.put(neighbor, current);
                        gScore.put(neighbor, tentativeG);
                        fScore.put(neighbor, tentativeG + heuristic(neighbor, goal));
            
                        if (!openSet.contains(neighbor)) {
                            openSet.add(neighbor);
                        }
                    }
                }
            }
    
            return Collections.emptyList(); // No path
        }
        
        /* ---------- Internal state ---------- */
        
        private static final Map<Node, Node> cameFrom = new HashMap<>();
        private static final Map<Node, Double> gScore = new HashMap<>();
        private static final Map<Node, Double> fScore = new HashMap<>();
        
        /* ---------- Helpers ---------- */
        
        private static double heuristic(Node a, Node b) {
            return Node.distanceBetween(a, b); // Euclidean (admissible)
        }
        
        private static List<Node> reconstructPath(Node goal) {
            LinkedList<Node> path = new LinkedList<>();
            Node current = goal;
            
            while (current != null) {
                path.addFirst(current);
                current = cameFrom.get(current);
            }
            
            return path;
        }
    }
</code>
