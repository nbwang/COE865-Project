package multicastnetwork;

import java.util.ArrayList;

public class MulticastNetwork
{
    public static void main (String args[])
    {
        ArrayList <Edge> edgeList = new ArrayList<Edge>();
        ArrayList <Receiver> recvrList = new ArrayList<Receiver>();
        ArrayList <Forwarder> forwarderList = new ArrayList<Forwarder>();
        Source source = null;
        Graph graph = new Graph();
        graph = graph.createGraph();
        ArrayList<Node> nodesList = graph.nodesList;

        for (Node n: nodesList){
            if (Character.compare(n.nodeType, 'F')==0){
                forwarderList.add((Forwarder)n);
            }
            else if (Character.compare(n.nodeType, 'S')==0){
                source = (Source) n;
            }
            else if (Character.compare(n.nodeType, 'R')==0){
                recvrList.add((Receiver)n);
            }
        }

        for (Edge e: edgeList){
              if (e.src.equals(source) || e.dst.equals(source)){
                source.getLinks().add(e);
            }
            for (Forwarder f: forwarderList){
                if (e.src.equals(f) || e.dst.equals(f)){
                    f.getLinks().add(e);
                }
            }
            for (Receiver r : recvrList){
                if (e.src.equals(r) || e.dst.equals(r)){
                    r.getLinks().add(e);
                }
            }
        }


        for (Receiver r : recvrList){
            source.generateShortestPath(BellmanFord.BellmanFordAlgorithm(graph, source, r));
        }
        
        int nodeIndex;
        for (ArrayList<Node> list: source.getShortestPaths()){
            for (Node n : list){
                nodeIndex = list.indexOf(n);
                if (n.equals(source)){
                    for (Forwarder f :forwarderList){
                        if (list.get(nodeIndex + 1).equals(f) && !source.getMulticastGroup().getRecvrList().contains(f)){
                            source.getMulticastGroup().getRecvrList().add(f);
                            f.getaddressesCheck().add(source.getMulticastGroup().getMulticastAddress());
                            break;
                        }
                    }
                }
                for (Forwarder f :forwarderList){
                    if (n.equals(f)){
                        for (Forwarder f2: forwarderList){
                            if (list.get(nodeIndex + 1).equals(f2) && !f.getMulticastGroup().getRecvrList().contains(f2)){
                                f.getMulticastGroup().getRecvrList().add(f2);
                                f2.getaddressesCheck().add(f.getMulticastGroup().getMulticastAddress());
                                break;
                            }
                        }
                        for (Receiver r : recvrList){
                            if (list.get(nodeIndex + 1).equals(r) && !f.getMulticastGroup().getRecvrList().contains(r)){
                                f.getMulticastGroup().getRecvrList().add(r);
                                r.getaddressesCheck().add(f.getMulticastGroup().getMulticastAddress());
                                break;
                            }
                        }
                    }
                }
            }
        }

        System.out.println ("\n"+source.getAllShortestPaths());

        Thread sourceThread = new Thread(source);
        source.socketInit();
        ArrayList<Thread> forwarderThreadList = new ArrayList<Thread>();
        ArrayList<Thread> recvrThreadList = new ArrayList<Thread>();

        for (Forwarder f :forwarderList){
            f.socketInit();
            Thread fThread = new Thread(f);
            forwarderThreadList.add(fThread);
            fThread.start();
        }

        for (Receiver r : recvrList){
            r.socketInit();
            Thread rThread = new Thread(r);
            recvrThreadList.add(rThread);
            rThread.start();
        }
        sourceThread.start();

        try{
            sourceThread.join();

            for (Thread thread: forwarderThreadList){
                thread.join();
            }

            for (Thread thread: recvrThreadList){
                thread.join();
            }
        }
        catch (InterruptedException e){
            System.out.println (e.getMessage());
        }
        System.out.println ("Packet Route:\n");
        
        for (int i = 0 ; i < forwarderList.size() ; i++){
            if (!forwarderList.get(i).getaddressesCheck().isEmpty()){
                forwarderList.get(i).output();
            }
        }
        for (int i = 0 ; i < recvrList.size() ; i++){
            recvrList.get(i).output();
        }
    }
}