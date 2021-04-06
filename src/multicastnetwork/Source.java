package multicastnetwork;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

public class Source extends Node implements Runnable{
    private ArrayList<ArrayList<Node>> shortestPaths;

    public Source (int id, int port){
        super (id, port, 'S');
        this.shortestPaths = new ArrayList<ArrayList<Node>>();
    }

    public void generateShortestPath(ArrayList<Node> list){
        this.shortestPaths.add(list);
    }

    public String getAllShortestPaths(){
        String output = "";
        for (ArrayList<Node> list : this.getShortestPaths()){
            output = output + "Path from Source to " + list.get(list.size()-1).id + ":\n";
            for (Node n : list){
                if(Character.compare(n.nodeType,'R') ==0){
                    output = output + n.getId();
                    break;
                }                
                output = output + n.getId()+ " --> ";    
            }
            output = output + "\n";
        }
        return output;
    }

    public ArrayList<ArrayList<Node>> getShortestPaths(){
        return shortestPaths;
    }

    public void setShortestPaths(ArrayList<ArrayList<Node>> shortestPaths){
        this.shortestPaths = shortestPaths;
    }

    public void socketInit(){
        try{
            this.socket = new DatagramSocket(this.getmCastGroup().getSendPort());
        }
        catch (SocketException e){
            System.out.println (e.getMessage());
        }
    }

    public void run(){
        String message = "Multicast message sent from Source.\n";
        byte[] buffer = message.getBytes();
        DatagramPacket packet;
        try{
            InetAddress addrToSend = InetAddress.getByName(this.getmCastGroup().getmAddr());
            packet = new DatagramPacket(buffer, buffer.length, addrToSend, this.getmCastRecvPort());
            this.getSocket().send(packet);
        }
        catch (IOException e){
            System.out.println (e.getMessage());
        }
    }
}
