package multicastnetwork;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

public class Source extends Node implements Runnable
{
    private ArrayList<ArrayList<Node>> shortestPaths;

    public Source (int id, int port)
    {
        super (id, port, 'S');
        this.shortestPaths = new ArrayList<ArrayList<Node>>();
    }

    public void generateShortestPath(ArrayList<Node> list)
    {
        this.shortestPaths.add(list);
    }

    public String getAllShortestPaths()
    {
        String output = "";
        for (int i = 0 ; i < this.getShortestPaths().size() ; i++)
        {
            output = output + "Path from Source to: " + "\n";
            for (int j = 0 ; j < this.getShortestPaths().get(i).size() ; j++)
            {
                output = output + this.getShortestPaths().get(i).get(j).id + " ";
            }
            output = output + "\n";
        }
        return output;
    }

    public ArrayList<ArrayList<Node>> getShortestPaths()
    {
        return shortestPaths;
    }

    public void setShortestPaths(ArrayList<ArrayList<Node>> shortestPaths)
    {
        this.shortestPaths = shortestPaths;
    }

    public void setupAllSockets()
    {
        try
        {
            this.socket = new DatagramSocket(this.getmCastGroup().getSendPort());
        }
        catch (SocketException e)
        {
            System.out.println (e.getMessage());
        }
    }

    public void run()
    {
        String message = "Packet from Source!";
        byte[] buffer = message.getBytes();
        DatagramPacket packet;
        try
        {
            InetAddress addrToSend = InetAddress.getByName(this.getmCastGroup().getmAddr());
            packet = new DatagramPacket(buffer, buffer.length, addrToSend, this.getmCastRecvPort());
            this.getSocket().send(packet);
        }
        catch (IOException e)
        {
            System.out.println (e.getMessage());
        }
        System.out.println ("Source Thread Sent Packet!\n");
    }

    public String toString()
    {
        return "Source:\n" + super.toString();
    }
}

