package multicastnetwork;

import java.io.IOException;
import java.net.*;

public class Receiver extends Node implements Runnable
{
    public Receiver (int id, int port)
    {
        super (id, port, 'R');
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
        try
        {
            this.getmSocket().receive(this.getRecvdPack());
        }
        catch (IOException e)
        {
            System.out.println (e.getMessage());
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
        return "Receiver:\n" + super.toString();
    }
}