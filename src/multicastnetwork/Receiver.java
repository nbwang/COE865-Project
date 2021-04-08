package multicastnetwork;

import java.io.IOException;
import java.net.*;

public class Receiver extends Node implements Runnable{
    public Receiver (int id, int port){
        super (id, port, 'R');
    }

    private DatagramPacket packetIn;

    public void socketInit(){
        try{
            this.socket = new DatagramSocket(this.getPort());
            if (this.getaddressesCheck().isEmpty() != true){
                this.multicastSocket.joinGroup(InetAddress.getByName(this.getaddressesCheck().get(0)));
            }
        }
        catch (IOException e){
            System.out.println (e.getMessage());
        }
    }

    public void run(){
        byte[] inBuffer = new byte[1024];
        this.packetIn = new DatagramPacket(inBuffer, inBuffer.length);
        try{
            this.getMulticastSocket().receive(this.getpacketIn());
        }
        catch (IOException e){
            System.out.println (e.getMessage());
        }
    }

    public void output(){
        String steps = new String (this.packetIn.getData(), 0, this.packetIn.getLength());
        System.out.println ("Receiver " + this.getId() + " Received:\n" + steps);
    }

    public DatagramPacket getpacketIn(){
        return packetIn;
    }

    public void setpacketIn(DatagramPacket packetIn){
        this.packetIn = packetIn;
    }
}