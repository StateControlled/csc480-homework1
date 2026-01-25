package edu.depaul.wberthou.graph;

import edu.depaul.wberthou.util.FileReader;
import edu.depaul.wberthou.util.MathUtils;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

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

    public void print() {
        nodes.forEach(System.out::println);
        System.out.println();
        arcs.forEach(System.out::println);
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
