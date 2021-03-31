package multicastnetwork;

import java.util.*;
import java.lang.*;
import java.io.*; 


public class BellmanFord {
    public static ArrayList<Node> BellmanFord(Graph graph, Node Source, Node Destination){
        ArrayList<Node> path = new ArrayList<Node>();
        path.add(Source);
        int numberOfVertices = graph.nodesList.size();

        //Creates an array to hold the destination distances
        int dist[] = new int[numberOfVertices];
        for(int i = 0; i < numberOfVertices; i++){
            dist[i] = Integer.MAX_VALUE;
        }
        dist[Source.getId()] = 0;

        for(int  i = 1; i < graph.nodesList.size(); i++){
            for(Edge e : graph.edgesList){
                if(dist[e.src.getId()] != Integer.MAX_VALUE &&  (dist[e.src.getId()] + e.weight) < dist[e.dest.getId()]){
                    if(dist[e.dst.getId()] == dist[Destination.getId()]){
                        path.add(e.dst);
                    }
                    dist[e.dst.getId()] = dist[e.src.getId()] + e.weight;
                }
            }
        }
        return path;
    }
}