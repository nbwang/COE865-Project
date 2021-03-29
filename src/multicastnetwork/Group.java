package multicastnetwork;

import java.net.*;

public class Group {
    private String groupAddress;
    private InetAddress group;
    
    public Group(String groupAddress, InetAddress group){
        this.groupAddress = groupAddress;
        this.group = group;              
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

    public InetAddress getGroup() {
        return group;
    }
}
