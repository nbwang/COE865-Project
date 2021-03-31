package multicastnetwork;

import java.io.IOException;
import java.net.*;

public abstract class Node {
    protected DatagramSocket UDPsocket;
    protected MulticastSocket multiSocket; 
    protected char nodeType;
    protected int id;
    protected int port;
    protected String address = "127.0.0.1";
    
    public Node(int id, int port, char nodeType){
        this.id = id;
        this.port = port;
        this.nodeType = nodeType;
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

    public char getNodeType() {
        return nodeType;
    }

    public void setNodeType(char nodeType) {
        this.nodeType = nodeType;
    }
    
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
