package edu.depaul.wberthou.graph;

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

    public Node getFirst() {
        return first;
    }

    public void setFirst(Node first) {
        this.first = first;
    }

    public Node getSecond() {
        return second;
    }

    public void setSecond(Node second) {
        this.second = second;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

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