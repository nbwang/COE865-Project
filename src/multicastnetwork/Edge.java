package multicastnetwork;

import java.net.*;

public class Edge {
    public int weight;  
    public Node src;
    public Node dst;

    public Edge(int weight, Node src, Node dst) {
        this.weight = weight;
        this.src = src;
        this.dst = dst;
    }
    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setSrc(Node src) {
        this.src = src;
    }

    public void setDst(Node dst) {
        this.dst = dst;
    }

    public int getWeight() {
        return weight;
    }

    public Node getSrc() {
        return src;
    }

    public Node getDst() {
        return dst;
    }
}