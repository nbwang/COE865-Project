package multicastnetwork;

import java.io.IOException;
import java.net.*;

public abstract class Node {
    protected DatagramSocket UDPsocket;
    protected MulticastSocket multiSocket; 
    private int id;
    private int port;
    private String address = "127.0.0.1";
    
    public Node(int id, int port){
        this.id = id;
        this.port = port;
        try {
            this.multiSocket = new MulticastSocket(65010);
        } catch (IOException e){
            System.out.println(e.getMessage());
        }       
   
    }

    public MulticastSocket getMultiSocket() {
        return multiSocket;
    }

    public void setMultiSocket(MulticastSocket multiSocket) {
        this.multiSocket = multiSocket;
    }
    
    public abstract void setUpSocket();
    
    public int getId() {
        return id;
    }

    public int getPort() {
        return port;
    }

    public String getAddress() {
        return address;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setAddress(String address) {
        this.address = address;
    }   
    
}
