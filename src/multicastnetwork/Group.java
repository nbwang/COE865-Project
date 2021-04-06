package multicastnetwork;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Group
{
    private int sourceID;
    private ArrayList<Node> recvrList;
    private int sendPort;
    private String mAddr = "230.0.0.";
    private static int mAddrEnd = 1;
    private InetAddress group;

    public Group (int sourceID, int sendPort)
    {
        this.sourceID = sourceID;
        this.sendPort = sendPort;
        this.recvrList = new ArrayList<Node>();
        this.mAddr = this.mAddr + Integer.toString(mAddrEnd);
        try
        {
            this.group = InetAddress.getByName(this.mAddr);
        }
        catch (UnknownHostException e)
        {
            System.out.println (e.getMessage());
        }

        mAddrEnd++;
    }

    public String toString ()
    {
        String output = "";
        output = "Group: -> Belongs to Node: " + this.getSourceID() + "\n";
        output = output + "Send Port for Sender Socket: " + this.getSendPort() + "\n";
        output = output + "Group Address: " + this.getmAddr() + "\n";
        output = output + "Receiver List:";
        for (int i = 0 ; i < this.getRecvrList().size() ; i++)
        {
            output = output  + "\n" + this.getRecvrList().get(i).getId();
        }
        return output;
    }

    public InetAddress getGroup()
    {
        return group;
    }

    public void setGroup(InetAddress group)
    {
        this.group = group;
    }

    public String getmAddr()
    {
        return mAddr;
    }

    public void setmAddr(String mAddr)
    {
        this.mAddr = mAddr;
    }

    public static int getmAddrEnd()
    {
        return mAddrEnd;
    }

    public static void setmAddrEnd(int mAddrEnd)
    {
        Group.mAddrEnd = mAddrEnd;
    }

    public int getSourceID()
    {
        return sourceID;
    }

    public void setSourceID(int sourceID)
    {
        this.sourceID = sourceID;
    }

    public ArrayList<Node> getRecvrList()
    {
        return recvrList;
    }

    public void setRecvrList(ArrayList<Node> recvrList)
    {
        this.recvrList = recvrList;
    }

    public int getSendPort()
    {
        return sendPort;
    }

    public void setSendPort(int sendPort)
    {
        this.sendPort = sendPort;
    }
}