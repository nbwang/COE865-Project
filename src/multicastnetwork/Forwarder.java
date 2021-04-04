package multicastnetwork;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Forwarder extends Node implements Runnable{
    
    private DatagramPacket receivedPacket;
    
    public Forwarder(int id, int port){
        super(id, port, 'F');
    }
    
    @Override
    public void setUpSocket(int port){
      try {
          this.UDPsocket = new DatagramSocket(this.getmCastGroup().getPort());
      } catch (SocketException ex) {  
            Logger.getLogger(Source.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
    @Override
    public void run(){
        byte[] inputBuf = new byte[1024];
        this.receivedPacket = new DatagramPacket(inputBuf, inputBuf.length);
        String message1 = "Hello from forwarder";
//        System.out.println("Hello from forwarder");
        try {
            
            this.getMultiSocket().receive(this.receivedPacket);
            System.out.println("Hello from forwarder");
            System.out.println("!");
            String received = new String(this.getReceivedPacket().getData(), 0, this.getReceivedPacket().getLength());
            String message = "Forwarder: "+this.getId() + "is sending packet: \n" + received;
            byte[] outputBuf = message.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(outputBuf, outputBuf.length, this.getmCastGroup().getGroup(), this.getmCastRecvPort());
            this.getUDPsocket().send(sendPacket);
        } catch (IOException ex) {
            Logger.getLogger(Source.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public DatagramPacket getReceivedPacket() {
        return receivedPacket;
    }


}
