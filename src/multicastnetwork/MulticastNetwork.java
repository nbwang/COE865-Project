
package multicastnetwork;

import java.util.ArrayList;

public class MulticastNetwork {


    public static void main(String[] args) {
        Receiver receiver = new Receiver(0,0);
        Node source = new Source(0, 0);
        BellmanFord bellmanFord = new BellmanFord();
        Graph graph = new Graph();
        graph = graph.createGraph();
        
        ArrayList<ArrayList<Node>> list = new ArrayList<ArrayList<Node>>();
        ArrayList<Node> nodesList = graph.nodesList;
        ArrayList<Edge> edgesList = graph.edgesList;
        ArrayList<Node> receivers = new ArrayList<Node>();
   
        for (Node n : nodesList){
            if (Character.compare(n.nodeType, 'S')==0){
                source.setId(n.id);
                source.setPort(n.port);
            } 
        }
        
        for (Node n : nodesList){
            if (Character.compare(n.nodeType, 'R')==0){
                receiver = (Receiver) n;               
                list.add(bellmanFord.BellmanFordAlgorithm(graph, source, receiver));
                receiver.setShortestPath(bellmanFord.BellmanFordAlgorithm(graph, source, receiver));
                receivers.add(receiver);
            }
        }
        
    }
}
