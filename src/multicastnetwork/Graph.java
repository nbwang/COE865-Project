/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multicastnetwork;

import java.util.*;
import java.lang.*;
import java.io.*; 


public class Graph {
    public static void main (String args[])
    {
        ArrayList<Node> nodesList = new ArrayList<Node>();
        ArrayList<Edge> edgesList = new ArrayList<Edge>();
        boolean linebreak = false;
        int line = 0;
        File topology = new File ("topology.txt");
        // Source source;

        try{
            Scanner fileReader = new Scanner(topology);
            while (fileReader.hasNextLine())
            {
                line++;
                String lineData = fileReader.nextLine();
                if (lineData.equals("EDGES"));
                {
                    linebreak = true;
                    continue;
                }
                if (linebreak == false)
                {
                    String stParts [] = lineData.split(" ");
                    int nodeID = Integer.parseInt(stParts[0]);
                    char nodeType = stParts[1].charAt(0);
                    int nodePort = Integer.parseInt(stParts[2]);
                    switch(nodeType){
                        case 'S':
                            Source source = new Source(nodeID, nodePort);
                            nodesList.add(source);
                        case 'R':
                            Reciever reciever = new Receiver(nodeID, nodePort);
                            nodesList.add(reciever);
                        case 'F':
                            Forwarder forwarder = new Forwarder(nodeID, nodePort);
                            nodesList.add(forwarder);
                    }
                }
                else
                {
                    // edgesList.add(lineData);
                    String stParts [] = lineData.split(" ");
                    int edgeWeight = Integer.parseInt(stParts[0]);
                    int edgeSrc = Integer.parseInt(stParts[1]);
                    int edgeDest = Integer.parseInt(stParts[2]);

                    Node src;
                    Node dest;
                    for(Node n: nodesList){
                        if(n.getId() == src){
                            src = n;
                        }
                        else if (n.getId() == dest){
                            dest = n;
                        }
                    }

                    Edge edge = new Edge(edgeWeight, edgeSrc, edgeDest);
                    edgesList.add(edge);
                
                }
            }
            fileReader.close();
        }
        catch (IOException e)
        {
            System.out.println (e.getMessage());
        }
    }

    class Edge {
        int weight;
        Node src;
        Node dest;

        public Edge(int weight, Node src, Node dest){
            this.weight = weight;
            this.src = src;
            this.dest = dest;
        }
                   
    }

}