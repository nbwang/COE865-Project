package multicastnetwork;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Group{
    private int sourceID;
    private ArrayList<Node> recvrList;
    private int port;
    private String multicastAddress = "235.255.255.10";
    private static int addressSuffix = 1;
    private InetAddress group;

    public Group (int sourceID, int port){
        this.sourceID = sourceID;
        this.port = port;
        this.recvrList = new ArrayList<Node>();
        this.multicastAddress = this.multicastAddress + Integer.toString(addressSuffix);

        try{
            this.group = InetAddress.getByName(this.multicastAddress);
        }
        catch (UnknownHostException e){
            System.out.println (e.getMessage());
        }
        addressSuffix++;
    }

    public InetAddress getGroup(){
        return group;
    }

    public void setGroup(InetAddress group){
        this.group = group;
    }

    public String getMulticastAddress(){
        return multicastAddress;
    }

    public void setMulticastAddress(String multicastAddress){
        this.multicastAddress = multicastAddress;
    }

    public static int getAddressSuffix(){
        return addressSuffix;
    }

    public static void setAddressSuffix(int addressSuffix){
        Group.addressSuffix = addressSuffix;
    }

    public int getSourceID(){
        return sourceID;
    }

    public void setSourceID(int sourceID){
        this.sourceID = sourceID;
    }

    public ArrayList<Node> getRecvrList(){
        return recvrList;
    }

    public void setRecvrList(ArrayList<Node> recvrList){
        this.recvrList = recvrList;
    }

    public int getPort(){
        return port;
    }

    public void setPort(int port){
        this.port = port;
    }
}