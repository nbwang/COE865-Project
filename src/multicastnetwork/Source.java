package multicastnetwork;

import java.io.IOException;
import java.net.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Source extends Node implements Runnable{
    private ArrayList<Node> shortestPath;
    
    public Source(int id, int port){
        super(id, port, 'S');      
    }
    
    @Override
    public void setUpSocket(int port){
      try {
          this.UDPsocket = new DatagramSocket(this.getmCastGroup().getPort());
      } catch (SocketException ex) {  
            Logger.getLogger(Source.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }

    public void setShortestPath(ArrayList<Node> shortestPath) {
        this.shortestPath = shortestPath;
    } 
    
    @Override
    public void run(){
        String message = "Multicast Packet";
        byte[] buf = message.getBytes();
        DatagramPacket packet;
        try {
            System.out.println(message);
            InetAddress address = InetAddress.getByName(this.getmCastGroup().getmAddr());
            packet = new DatagramPacket(buf, buf.length, address, this.getmCastRecvPort());
            this.getUDPsocket().send(packet);
        } catch (IOException ex) {
            Logger.getLogger(Source.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}

    
