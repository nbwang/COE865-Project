package multicastnetwork;

import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Group {
    private int port;
    private int SourceID;
    private String groupAddress;
    private String mAddr = "230.0.0";
    private static int mAddrsuf = 1;
    private InetAddress group;
    
    public Group(int SourceID, int port){
        this.SourceID = SourceID;
        this.port = port; 
        this.mAddr = this.mAddr + Integer.toString(mAddrsuf);
        try {
            this.group = InetAddress.getByName(mAddr);
        } catch (UnknownHostException ex) {
            Logger.getLogger(Group.class.getName()).log(Level.SEVERE, null, ex);
        }
        mAddrsuf++;
    }

    public void setGroupAddress(String groupAddress) {
        this.groupAddress = groupAddress;
    }

    public void setGroup(InetAddress group) {
        this.group = group;
    }

    public String getGroupAddress() {
        return groupAddress;
    }

    public int getPort() {
        return port;
    }

    public int getSourceID() {
        return SourceID;
    }

    public String getmAddr() {
        return mAddr;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setSourceID(int SourceID) {
        this.SourceID = SourceID;
    }

    public void setmAddr(String mAddr) {
        this.mAddr = mAddr;
    }
    
    public InetAddress getGroup() {
        return group;
    }
}
