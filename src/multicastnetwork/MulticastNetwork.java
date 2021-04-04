
package multicastnetwork;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MulticastNetwork {

    public static void main(String[] args) {
//        Receiver receiver = new Receiver(7,40006);
//        Source source = new Source(1, 40000);
//        Forwarder forwarder = new Forwarder(0,0);
        String message = "Hello, this is a message";
        BellmanFord bellmanFord = new BellmanFord();
        Graph graph = new Graph();
        graph = graph.createGraph();
        
//        bellmanFord.BellmanFordAlgorithm(graph, source, receiver);
        
        ArrayList<ArrayList<Node>> list = new ArrayList<ArrayList<Node>>();
        ArrayList<Node> nodesList = graph.nodesList;
        ArrayList<Edge> edgesList = graph.edgesList;
        ArrayList<Thread> receiversThreads = new ArrayList<Thread>();
        ArrayList<Thread> forwardersThreads = new ArrayList<Thread>();
        
        Source source = new Source(0,0);
        for (Node n : nodesList){
            if (Character.compare(n.nodeType, 'S')==0){
                source.setId(n.id);
                source.setPort(n.port);
                source.setUpSocket(source.getPort());
                
            } 
        }
        Thread sourceThread = new Thread(source);
        for (Node n : nodesList){
            if (Character.compare(n.nodeType, 'F')==0){
                Forwarder forwarder;
                forwarder = (Forwarder) n;               
//                list.add(bellmanFord.BellmanFordAlgorithm(graph, source, receiver));
//                receiver.setShortestPath(bellmanFord.BellmanFordAlgorithm(graph, source, receiver));
                forwarder.setUpSocket(forwarder.getPort());
                forwardersThreads.add(new Thread(forwarder));
            }
        }
        
        for (Node n : nodesList){
            if (Character.compare(n.nodeType, 'R')==0){
                Receiver receiver;
                receiver = (Receiver) n;               
                list.add(bellmanFord.BellmanFordAlgorithm(graph, source, receiver));
                receiver.setShortestPath(bellmanFord.BellmanFordAlgorithm(graph, source, receiver));
                receiver.setUpSocket(receiver.getPort());
                receiversThreads.add(new Thread(receiver));
            }
        }
        sourceThread.start();
        for (int i = 0; i < forwardersThreads.size(); i++){
            forwardersThreads.get(i).start();
        }
        
        for (int i = 0; i < receiversThreads.size(); i++){
            receiversThreads.get(i).start();
        }
        
        
        
        try {
            
            sourceThread.join();
            
            for (int i = 0; i < forwardersThreads.size(); i++){
                forwardersThreads.get(i).join();
            }
            
            for (int i = 0; i < receiversThreads.size(); i++){
                receiversThreads.get(i).join();
            }
               
        } catch (InterruptedException ex) { 
            Logger.getLogger(MulticastNetwork.class.getName()).log(Level.SEVERE, null, ex);
        }
        ArrayList<Node> shortestPath1 = new ArrayList<Node>();
        
//        for (int i = 0; i < shortestPath1.size(); i++){
//            char c = shortestPath1.get(i).nodeType;
//            switch(c){
//                case 'S':
//                    Source s = (Source) shortestPath1.get(i);
//                    
//                    break;
//                case 'F':
//                    Forwarder f = (Forwarder) shortestPath1.get(i);
//                    break;
//                case 'R':
//                    Receiver r = (Receiver) shortestPath1.get(i);
//            }
//        }
    }
}
