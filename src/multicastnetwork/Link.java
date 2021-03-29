package multicastnetwork;

import java.net.*;

public class Link {
    private int cost;
    private int src;
    private int dst;
    private int srcPort;
    private int dstPort;

    public Link(int cost, int src, int dst, int srcPort, int dstPort) {
        this.cost = cost;
        this.src = src;
        this.dst = dst;
        this.srcPort = srcPort;
        this.dstPort = dstPort;
    }

    public int getCost() {
        return cost;
    }

    public int getSrc() {
        return src;
    }

    public int getDst() {
        return dst;
    }

    public int getSrcPort() {
        return srcPort;
    }

    public int getDstPort() {
        return dstPort;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setSrc(int src) {
        this.src = src;
    }

    public void setDst(int dst) {
        this.dst = dst;
    }

    public void setSrcPort(int srcPort) {
        this.srcPort = srcPort;
    }

    public void setDstPort(int dstPort) {
        this.dstPort = dstPort;
    }
    
}
