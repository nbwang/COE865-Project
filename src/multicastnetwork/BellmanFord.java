package multicastnetwork;

import java.util.*;
import java.lang.*;
import java.io.*; 


public class BellmanFord {
    public static ArrayList<Node> BellmanFordAlgorithm(Graph graph, Node Source, Node Destination){
        ArrayList<Node> path = new ArrayList<Node>();

        path.add(Source);
        int numberOfVertices = graph.nodesList.size();

        //Creates an array to hold the destination distances
        int dist[] = new int[numberOfVertices];
        ArrayList<ArrayList<Node>> distNodes = new ArrayList<ArrayList<Node>>(numberOfVertices);
        for(int i = 0; i < numberOfVertices; i++){
            distNodes.add(path);
        }
        for(int i = 0; i < numberOfVertices; i++){
            dist[i] = Integer.MAX_VALUE;
        }
        dist[Source.id] = 0;
        path.add(Source);
        distNodes.set(Source.id, path);
        

        for(int  i = 1; i < graph.nodesList.size(); i++){
            for(Edge e : graph.edgesList){
                if(dist[e.src.id] != Integer.MAX_VALUE && !distNodes.get(e.src.id).isEmpty() &&(dist[e.src.id] + e.weight) < dist[e.dst.id]){
                    path.clear();
                    path = distNodes.get(e.src.id);
                    distNodes.set(e.dst.id, path);
                    dist[e.dst.id] = dist[e.src.id] + e.weight;
                }
            }
        }
        path = distNodes.get(Destination.id-1);
        System.out.println(distNodes.get(2).size());
        for(Node n : path)
          System.out.println(n.id);
        return path;
    }

    public static void main(String[] args){
        Graph graph = new Graph();
        graph = graph.createGraph();
        Receiver receiver = new Receiver(7,40006);
        Source source = new Source(1, 40000);
        BellmanFordAlgorithm(graph,source,receiver);
    }
}
