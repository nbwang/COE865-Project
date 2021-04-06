package multicastnetwork;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

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


        boolean check = false;
        for (int i = 0 ; i < source.getShortestPaths().size() ; i++)
        {
            for (int j = 0 ; j < source.getShortestPaths().get(i).size() - 1 ; j++)
            {
                //If node in path is equal to source
                boolean sourceCheck = false;
                check = false;
                if (source.getShortestPaths().get(i).get(j).equals(source))
                {
                    check = false;
                    for (int k = 0 ; k < forwarderList.size() ; k++)
                    {
                        if (source.getShortestPaths().get(i).get(j + 1).equals(forwarderList.get(k)))
                        {
                            if (source.getmCastGroup().getRecvrList().contains(forwarderList.get(k)) == false)
                            {
                                source.getmCastGroup().getRecvrList().add(forwarderList.get(k));
                                forwarderList.get(k).getApartOfAddresses().add(source.getmCastGroup().getmAddr());
                            }
                            check = true;
                            sourceCheck = true;
                            break;
                        }
                    }
                    if (check == false)
                    {
                        for (int k = 0 ; k < recvrList.size() ; k++)
                        {
                            if (source.getShortestPaths().get(i).get(j + 1).equals(recvrList.get(k)))
                            {
                                if (source.getmCastGroup().getRecvrList().contains(recvrList.get(k)) == false)
                                {
                                    source.getmCastGroup().getRecvrList().add(recvrList.get(k));
                                    recvrList.get(k).getApartOfAddresses().add(source.getmCastGroup().getmAddr());
                                }
                                sourceCheck = true;
                                break;
                            }
                        }
                    }
                }

                if (sourceCheck == true)
                {
                    continue;
                }

                check = false;
                //If node in path is equal to a node in forwarder list
                for (int n = 0 ; n < forwarderList.size() ; n++)
                {
                    if (source.getShortestPaths().get(i).get(j).equals(forwarderList.get(n)))
                    {
                        check = false;
                        for (int m = 0 ; m < forwarderList.size() ; m++)
                        {
                            if (source.getShortestPaths().get(i).get(j + 1).equals(forwarderList.get(m)))
                            {
                                if (forwarderList.get(n).getmCastGroup().getRecvrList().contains(forwarderList.get(m)) == false)
                                {
                                    forwarderList.get(n).getmCastGroup().getRecvrList().add(forwarderList.get(m));
                                    forwarderList.get(m).getApartOfAddresses().add(forwarderList.get(n).getmCastGroup().getmAddr());
                                }
                                check = true;
                                break;
                            }
                        }

                        if (check == false)
                        {
                            for (int m = 0 ; m < recvrList.size() ; m++)
                            {
                                if (source.getShortestPaths().get(i).get(j + 1).equals(recvrList.get(m)))
                                {
                                    if (forwarderList.get(n).getmCastGroup().getRecvrList().contains(recvrList.get(m)) == false)
                                    {
                                        forwarderList.get(n).getmCastGroup().getRecvrList().add(recvrList.get(m));
                                        recvrList.get(m).getApartOfAddresses().add(forwarderList.get(n).getmCastGroup().getmAddr());
                                    }
                                    check = true;
                                    break;
                                }
                            }
                        }
                    }
                    if (check == true)
                    {
                        break;
                    }
                }
            }
        }

        // System.out.println (source);
        // System.out.println ("");

        // for (int i = 0 ; i < forwarderList.size() ; i++)
        // {
        //     System.out.println (forwarderList.get(i));
        //     System.out.println ("");
        // }
        // for (int i = 0 ; i < recvrList.size() ; i++)
        // {
        //     System.out.println (recvrList.get(i));
        //     System.out.println ("");
        // }

        System.out.println (source.getAllShortestPaths());

        Thread sourceThread = new Thread(source);
        source.setupAllSockets();
        ArrayList<Thread> forwardThreadList = new ArrayList<Thread>();
        ArrayList<Thread> recvrThreadList = new ArrayList<Thread>();

        for (int i = 0 ; i < forwarderList.size() ; i++)
        {
            forwarderList.get(i).setupAllSockets();
            forwardThreadList.add(new Thread(forwarderList.get(i)));
        }

        for (int i = 0 ; i < recvrList.size() ; i++)
        {
            recvrList.get(i).setupAllSockets();
            recvrThreadList.add(new Thread(recvrList.get(i)));
        }

        for (int i = 0 ; i < recvrThreadList.size() ; i++)
        {
            recvrThreadList.get(i).start();
        }

        for (int i = 0 ; i < forwardThreadList.size() ; i++)
        {
            forwardThreadList.get(i).start();
        }

        sourceThread.start();
        try
        {
            sourceThread.join();

            for (int i = 0 ; i < recvrThreadList.size() ; i++)
            {
                recvrThreadList.get(i).join();
            }

            for (int i = 0 ; i < forwardThreadList.size() ; i++)
            {
                forwardThreadList.get(i).join();
            }
        }
        catch (InterruptedException e)
        {
            System.out.println (e.getMessage());
        }

        System.out.println ("PACKET TRACING:\n");

        for (int i = 0 ; i < forwarderList.size() ; i++)
        {
            if (forwarderList.get(i).getApartOfAddresses().isEmpty() != true)
            {
                forwarderList.get(i).getOut();
            }

        }

        for (int i = 0 ; i < recvrList.size() ; i++)
        {
            recvrList.get(i).getOut();
        }
    }
}