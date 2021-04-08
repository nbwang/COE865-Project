package multicastnetwork;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

public abstract class Node
{
    protected DatagramSocket socket;
    protected MulticastSocket multicastSocket;
    protected int id;
    protected char nodeType;
    private ArrayList<Edge> edges;
    protected int port;
    private int multicastPort;
    private Group multicastGroup;
    private ArrayList<String> addressesList;

    public Node(int id, int port, char nodeType){
        this.id = id;
        this.nodeType = nodeType;
        this.port = port;
        this.multicastPort = 4000;
        this.edges = new ArrayList<Edge>();
        try
        {
            this.multicastSocket = new MulticastSocket(this.multicastPort);
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
        this.multicastGroup = new Group (this.id, this.port);
        this.addressesList = new ArrayList<String>();
    }

    public abstract void socketInit();

    public ArrayList<String> getaddressesCheck()
    {
        return addressesList;
    }

    public void setAddressesList(ArrayList<String> addressesList)
    {
        this.addressesList = addressesList;
    }

    public MulticastSocket getMulticastSocket()
    {
        return multicastSocket;
    }

    public void setMulticastSocket(MulticastSocket multicastSocket)
    {
        this.multicastSocket = multicastSocket;
    }

    public int getMulticastPort()
    {
        return multicastPort;
    }

    public void setMulticastPort(int multicastPort)
    {
        this.multicastPort = multicastPort;
    }

    public Group getMulticastGroup()
    {
        return multicastGroup;
    }

    public void setMulticastGroup(Group multicastGroup)
    {
        this.multicastGroup = multicastGroup;
    }

    public DatagramSocket getSocket()
    {
        return socket;
    }

    public void setSocket(DatagramSocket socket)
    {
        this.socket = socket;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public ArrayList<Edge> getLinks()
    {
        return edges;
    }

    public void setLinks(ArrayList<Edge> edges)
    {
        this.edges = edges;
    }

    public int getPort()
    {
        return port;
    }

    public void setPort(int port)
    {
        this.port = port;
    }

    public String toString()
    {
        String output = "";
        output = output + "Node ID: " + this.getId() + "\nPort: " + this.getPort();
        for (int i = 0 ; i < this.getLinks().size() ; i++)
        {
            output = output + "\nLink " + i + ":\n" + this.getLinks().get(i);
        }
        output = output + "\n" + this.getMulticastGroup();
        if (this.getaddressesCheck().isEmpty() != true)
        {
            output = output + "\nApart of: " + this.getaddressesCheck().get(0);
        }
        return output;
    }
}