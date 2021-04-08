package multicastnetwork;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Forwarder extends Node implements Runnable{
    public Forwarder (int id, int port){
        super (id, port, 'F');
    }

    private DatagramPacket packetIn;

    public void socketInit(){
        try{
            this.socket = new DatagramSocket(this.getPort());
            if (this.getaddressesCheck().isEmpty() != true)
            {
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
        if (this.getaddressesCheck().isEmpty() != true){
            try{
                this.getMulticastSocket().receive(this.getpacketIn());
                String receiverTemp = new String (this.getpacketIn().getData(), 0, this.getpacketIn().getLength());
                receiverTemp = "Multicast Packet sent from Forwarder " + this.getId() + "\n" + receiverTemp;
                byte[] outBuffer = receiverTemp.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(outBuffer, outBuffer.length, this.getMulticastGroup().getGroup(), this.getMulticastPort());
                this.getSocket().send(sendPacket);
            }
            catch (IOException e){
                System.out.println ("Forwarder");
                System.out.println (e.getMessage());
            }
        }
    }

    public void output(){
        String steps = new String (this.packetIn.getData(), 0, this.packetIn.getLength());
        System.out.println ("Node " + this.getId() + " Received:\n" + steps);
    }

    public DatagramPacket getpacketIn(){
        return packetIn;
    }

    public void setpacketIn(DatagramPacket packetIn){
        this.packetIn = packetIn;
    }
}