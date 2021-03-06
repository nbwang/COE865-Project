/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multicastnetwork;

import java.util.*;
import java.io.*; 


public class Graph {
    ArrayList<Node> nodesList = new ArrayList<Node>();
    ArrayList<Edge> edgesList = new ArrayList<Edge>();

    public Graph createGraph(){
        Graph graph = new Graph();
        boolean linebreak = false;
        File topology = new File ("topology.txt");
        // Source source;

        try{
            Scanner fileReader = new Scanner(topology);
            while (fileReader.hasNextLine()){
                String lineData = fileReader.nextLine();
                if (lineData.equals("EDGES")){
                    linebreak = true;
                    continue;
                }
                if (linebreak == false){
                    String stParts [] = lineData.split(" ");
                    int nodeID = Integer.parseInt(stParts[0]);
                    char nodeType = stParts[1].charAt(0);
                    int nodePort = Integer.parseInt(stParts[2]);
                    switch(nodeType){
                        case 'S':
                            Source source = new Source(nodeID, nodePort);
                            graph.nodesList.add(source);
                            System.out.println("Source ID: "+source.id+" Port: "+source.port);
                            break;
                        case 'R':
                            Receiver receiver = new Receiver(nodeID, nodePort);
                            graph.nodesList.add(receiver);
                            System.out.println("Receiver ID: "+receiver.id+" Port: "+receiver.port);
                            break;
                        case 'F':
                            Forwarder forwarder = new Forwarder(nodeID, nodePort);
                            graph.nodesList.add(forwarder);
                            System.out.println("Forwarder ID: "+forwarder.id+" Port: "+forwarder.port);
                            break;
                    }
                }                
                else{
                    // edgesList.add(lineData);
                    String stParts [] = lineData.split(" ");
                    int edgeWeight = Integer.parseInt(stParts[0]);
                    int edgeSrc = Integer.parseInt(stParts[1]);
                    int edgeDest = Integer.parseInt(stParts[2]);

                    Node src = null;
                    Node dest = null;
                    for(Node n: graph.nodesList){
                        if(n.getId() == edgeSrc){
                            src = n;
                        }
                        else if (n.getId() == edgeDest){
                            dest = n;
                        }
                    }

                    Edge edge = new Edge(edgeWeight, src, dest);
                    graph.edgesList.add(edge);
                
                }
            }
            fileReader.close();
        }
        catch (IOException e){
            System.out.println (e.getMessage());
        }
        return graph;
    }
}