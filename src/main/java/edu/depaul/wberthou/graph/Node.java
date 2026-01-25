package edu.depaul.wberthou.graph;

import edu.depaul.wberthou.util.MathUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Node implements Comparable<Node> {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

    public void addChild(Node child) {
        this.children.add(child);
    }

    public List<Node> getChildren() {
        return children;
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

    @Override
    public int compareTo(Node other) {
//        // sort by x, then sort by y
//        int xCompare = Integer.compare(this.x, other.x);
//        if (xCompare != 0) {
//            return xCompare;
//        }
//        return Integer.compare(this.y, other.y);
        return (int) distanceBetween(this, other);
    }


    public static double distanceBetween(Node first, Node second) {
        return MathUtils.distance(first.x, first.y, second.x, second.y);
    }

}

