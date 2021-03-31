/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multicastnetwork;

import java.util.*;
import java.lang.*;
import java.io.*; 


public class BellmanFord {
    public static ArrayList<Node> BellmanFord(Graph graph, Node Source, Node Destination){
        ArrayList<Node> path = new ArrayList<Node>();
        path.add(Source);
        int numberOfVertices = graph.NodeList.size();

        //Creates an array to hold the destination distances
        int dist[] = new int[numberOfVertices];
        for(int i = 0; i < numberOfVertices; i++){
            dist[i] = Integer.MAX_VALUE;
        }
        dist[Source.getId()] = 0;

        for(int  i = 1; i < graph.NodeList.size(); i++){
            for(Edge e : graph.EdgeList){
                if(dist[e.getSource().getId()] != Integer.MAX_VALUE &&  (dist[e.getSource().getId()] + e.getWeight()) < dist[e.getDestination().getId()]){
                    if(dist[e.getDestination().getId()] == dist[Destination.getId()]){
                        path.add(e.destination);
                    }
                    dist[e.getDestination().getId()] = dist[e.getSource().getId()] + e.getWeight();
                }
            }
        }
        return path;
    }
}