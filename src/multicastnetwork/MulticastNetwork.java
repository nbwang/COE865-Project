
package multicastnetwork;

import java.util.ArrayList;
import java.util.Iterator;

public class MulticastNetwork {

    public static void main(String[] args) {
        Receiver receiver = new Receiver(7,40006);
        Source source = new Source(1, 40000);
        Forwarder forwarder = new Forwarder(0,0);
        String message = "Hello, this is a message";
        BellmanFord bellmanFord = new BellmanFord();
        Graph graph = new Graph();
        graph = graph.createGraph();
        
//        bellmanFord.BellmanFordAlgorithm(graph, source, receiver);
        
        ArrayList<ArrayList<Node>> list = new ArrayList<ArrayList<Node>>();
        ArrayList<Node> nodesList = graph.nodesList;
        ArrayList<Edge> edgesList = graph.edgesList;
        ArrayList<Receiver> receivers = new ArrayList<Receiver>();
        ArrayList<Forwarder> forwarders = new ArrayList<Forwarder>();
        for (Node n : nodesList){
            if (Character.compare(n.nodeType, 'S')==0){
                source.setId(n.id);
                source.setPort(n.port);
                source.setUpSocket(source.getPort());
                System.out.println(source.toString());
            } 
        }
        
        for (Node n : nodesList){
            if (Character.compare(n.nodeType, 'F')==0){
                forwarder = (Forwarder) n;               
//                list.add(bellmanFord.BellmanFordAlgorithm(graph, source, receiver));
//                receiver.setShortestPath(bellmanFord.BellmanFordAlgorithm(graph, source, receiver));
                forwarder.setUpSocket(forwarder.getPort());
                forwarders.add(forwarder);
                System.out.println(forwarder.toString());
            }
        }
        
        for (Node n : nodesList){
            if (Character.compare(n.nodeType, 'R')==0){
                receiver = (Receiver) n;               
                list.add(bellmanFord.BellmanFordAlgorithm(graph, source, receiver));
                receiver.setShortestPath(bellmanFord.BellmanFordAlgorithm(graph, source, receiver));
                receiver.setUpSocket(receiver.getPort());
                receivers.add(receiver);
                System.out.println(receiver.toString());
            }
        }
        
        ArrayList<Node> shortestPath1 = new ArrayList<Node>();
        shortestPath1.add(source);
        shortestPath1.add(forwarders.get(1));
        shortestPath1.add(forwarders.get(2));
        shortestPath1.add(receivers.get(2));
        receivers.get(2).setShortestPath(shortestPath1);
        
        for (int i = 0; i < shortestPath1.size(); i++){
            char c = shortestPath1.get(i).nodeType;
            System.out.println("Hello");
            switch(c){
                case 'S':
                    Source s = (Source) shortestPath1.get(i);
                    s.sendMessage();
                    break;
                case 'F':
                    Forwarder f = (Forwarder) shortestPath1.get(i);
                    f.sendMessage();
                    break;
                case 'R':
                    Receiver r = (Receiver) shortestPath1.get(i);
                    r.receiveMessage();
            }
        }
    }
}
