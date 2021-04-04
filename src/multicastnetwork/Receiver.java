package multicastnetwork;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Receiver extends Node implements Runnable {
    
        private ArrayList<Node> shortestPath;
        private DatagramPacket receivedPacket;
    
    
    public Receiver(int id, int port){
        super(id, port, 'R');
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
        byte[] inputBuf = new byte[1024];
        this.receivedPacket = new DatagramPacket(inputBuf, inputBuf.length);
        try {
            
            this.getMultiSocket().receive(receivedPacket);
            System.out.println("Hello From receiver");
        } catch (IOException ex) {
            Logger.getLogger(Receiver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
