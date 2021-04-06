package multicastnetwork;

import java.util.*;
import java.lang.*;
import java.io.*; 


public class BellmanFord {
    public static ArrayList<Node> BellmanFordAlgorithm(Graph graph, Node Source, Node Destination){
        ArrayList<Node> path = new ArrayList<Node>();
        int numberOfVertices = graph.nodesList.size();

        //Creates an array to hold the destination distances
        int dist[] = new int[numberOfVertices];
        ArrayList<ArrayList<Node>> distNodes = new ArrayList<ArrayList<Node>>(numberOfVertices);
        for(int i = 0; i < numberOfVertices; i++){
            distNodes.add(path);
        }
        path.add(Source);
        distNodes.set(Source.id-1, path);
        for(int i = 0; i < numberOfVertices; i++){
            dist[i] = Integer.MAX_VALUE;
        }
        dist[Source.id-1] = 0;
        
        for(int  i = 1; i < graph.nodesList.size(); i++){
            for(Edge e : graph.edgesList){
                if(dist[e.src.id-1] != Integer.MAX_VALUE && !distNodes.get(e.src.id-1).isEmpty() && (dist[e.src.id-1] + e.weight) <= dist[e.dst.id-1]){
                    // path.clear();
                    path = new ArrayList<>(distNodes.get(e.src.id-1));
                    path.add(e.dst);
                    distNodes.set(e.dst.id-1, path);
                    dist[e.dst.id-1] = dist[e.src.id-1] + e.weight;
                }
            }
        }
        path = distNodes.get(Destination.id-1);
        return path;
    }

}