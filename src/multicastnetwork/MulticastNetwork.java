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
        BellmanFord bellmanFord = new BellmanFord();

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
            Node host1;
            Node host2;
            host1 = e.src;
            host2 = e.dst;

            if (host1.equals(source) || host2.equals(source)){
                source.getLinks().add(e);
            }
            for (Forwarder f: forwarderList){
                if (host1.equals(f) || host2.equals(f)){
                    f.getLinks().add(e);
                }
            }
            for (Receiver r : recvrList){
                if (host1.equals(r) || host2.equals(r)){
                    r.getLinks().add(e);
                }
            }
        }


        for (Receiver r : recvrList){
            source.generateShortestPath(bellmanFord.BellmanFordAlgorithm(graph, source, r));
        }
        
        int nodeIndex;
        for (ArrayList<Node> list: source.getShortestPaths()){
            for (Node n : list){
                nodeIndex = list.indexOf(n);
                //If node in path is equal to source
                if (n.equals(source)){
                    for (Forwarder f :forwarderList){
                        if (list.get(nodeIndex + 1).equals(f) && !source.getmCastGroup().getRecvrList().contains(f)){
                            source.getmCastGroup().getRecvrList().add(f);
                            f.getApartOfAddresses().add(source.getmCastGroup().getmAddr());
                            break;
                        }
                    }
                }
                //If node in path is equal to a node in forwarder list
                for (Forwarder f :forwarderList){
                    if (n.equals(f)){
                        for (Forwarder f2: forwarderList){
                            if (list.get(nodeIndex + 1).equals(f2) && !f.getmCastGroup().getRecvrList().contains(f2)){
                                f.getmCastGroup().getRecvrList().add(f2);
                                f2.getApartOfAddresses().add(f.getmCastGroup().getmAddr());
                                break;
                            }
                        }
                        for (Receiver r : recvrList){
                            if (list.get(nodeIndex + 1).equals(r) && !f.getmCastGroup().getRecvrList().contains(r)){
                                f.getmCastGroup().getRecvrList().add(r);
                                r.getApartOfAddresses().add(f.getmCastGroup().getmAddr());
                                break;
                            }
                        }
                    }
                }
            }
        }

        System.out.println (source);
        System.out.println ("");

        for (Forwarder f :forwarderList){
            System.out.println (f);
            System.out.println ("");
        }
        for (Receiver r : recvrList){
            System.out.println (r);
            System.out.println ("");
        }

        System.out.println (source.getAllShortestPaths());

        Thread sourceThread = new Thread(source);
        source.setupAllSockets();
        ArrayList<Thread> forwarderThreadList = new ArrayList<Thread>();
        ArrayList<Thread> recvrThreadList = new ArrayList<Thread>();

        for (Forwarder f :forwarderList){
            f.setupAllSockets();
            Thread fThread = new Thread(f);
            forwarderThreadList.add(fThread);
            fThread.start();
        }

        for (Receiver r : recvrList){
            r.setupAllSockets();
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

        System.out.println ("PACKET TRACING:\n");

        for (int i = 0 ; i < forwarderList.size() ; i++){
            if (!forwarderList.get(i).getApartOfAddresses().isEmpty()){
                forwarderList.get(i).getOut();
            }

        }

        for (int i = 0 ; i < recvrList.size() ; i++){
            recvrList.get(i).getOut();
        }
    }
}