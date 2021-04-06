package multicastnetwork;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Forwarder extends Node implements Runnable{
    public Forwarder (int id, int port){
        super (id, port, 'F');
    }

    private DatagramPacket recvdPack;

    public void setupAllSockets()
    {
        try
        {
            this.socket = new DatagramSocket(this.getPort());
            if (this.getApartOfAddresses().isEmpty() != true)
            {
                this.mSocket.joinGroup(InetAddress.getByName(this.getApartOfAddresses().get(0)));
            }
        }
        catch (IOException e)
        {
            System.out.println (e.getMessage());
        }
    }

    public void run()
    {
        byte[] inputBuffer = new byte[1024];
        this.recvdPack = new DatagramPacket(inputBuffer, inputBuffer.length);
        if (this.getApartOfAddresses().isEmpty() != true)
        {
            try
            {
                this.getmSocket().receive(this.getRecvdPack());
                String recvd = new String (this.getRecvdPack().getData(), 0, this.getRecvdPack().getLength());
                recvd = "Node " + this.getId() + " is Sending this Packet!\n" + recvd;
                byte[] sendBuf = recvd.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendBuf, sendBuf.length, this.getmCastGroup().getGroup(), this.getmCastRecvPort());
                this.getSocket().send(sendPacket);
            }
            catch (IOException e)
            {
                System.out.println ("Forwarder");
                System.out.println (e.getMessage());
            }
        }
    }

    public void getOut()
    {
        String test = new String (this.recvdPack.getData(), 0, this.recvdPack.getLength());
        System.out.println ("Node " + this.getId() + " Received:\n" + test);
    }

    public DatagramPacket getRecvdPack()
    {
        return recvdPack;
    }

    public void setRecvdPack(DatagramPacket recvdPack)
    {
        this.recvdPack = recvdPack;
    }

    public String toString()
    {
        return "Forwarder:\n" + super.toString();
    }
}