package multicastnetwork;

import java.net.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Forwarder extends Node{
    private ArrayList<Node> shortestPath;
    
    public Forwarder(int id, int port){
        super(id, port);
    }
    
    @Override
    public void setUpSocket(){
      try {
          this.UDPsocket = new DatagramSocket(this.getPort());
      } catch (SocketException ex) {  
            Logger.getLogger(Source.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }

    public void setShortestPath(ArrayList<Node> shortestPath) {
        this.shortestPath = shortestPath;
    }
    
}
