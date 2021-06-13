package com.company;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;

import static org.apache.zookeeper.CreateMode.PERSISTENT_SEQUENTIAL;

public class Executor  implements Watcher
{
    String znode;

    ZooKeeper zk;

    public Executor(String hostPort) throws KeeperException, IOException {
        zk = new ZooKeeper(hostPort, 600000, this);
        String path = "/lock";
        byte[] data = {1,2,3};
        ACL acl = new ACL(ZooDefs.Perms.ALL,ZooDefs.Ids.ANYONE_ID_UNSAFE);
        List<ACL> aclList = new ArrayList<ACL>();
        aclList.add(acl);

        CreateMode createMode = PERSISTENT_SEQUENTIAL;
        Stat stat;
        try {

            String Znode = zk.create(path,data,aclList,createMode);
            System.out.println("Node si Znode"+ Znode);
            List<String> childnodes = new ArrayList<String>();
            childnodes = zk.getChildren("/",false);
            System.out.println("Node si childnodes"+ childnodes.get(0));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Created node");

        //Creating 5 client.
        String host_port = "127.0.0.1:2181";
        ZooClient zooClient = new ZooClient(host_port);
         host_port = "127.0.0.1:2182";
        ZooClient zooClient1 = new ZooClient(host_port);
         host_port = "127.0.0.1:2183";
        ZooClient zooClient2 = new ZooClient(host_port);
         host_port = "127.0.0.1:2184";
        ZooClient zooClient3 = new ZooClient(host_port);
         host_port = "127.0.0.1:2185";
        ZooClient zooClient4 = new ZooClient(host_port);

        zooClient.run();
        zooClient1.run();
        zooClient2.run();
        zooClient3.run();
        zooClient4.run();


    }

    public static void main(String[] args) {
        String hostPort = "127.0.0.1:2181";
        try {
            new Executor(hostPort);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void process(WatchedEvent event) {
        System.out.println(" Dummy Exists" + event.toString());
    }

    public void run() {
        System.out.println(" Dummy Run");
    }

    public void closing(int rc) {
        System.out.println(" Dummy Closing");
    }



    public void exists(byte[] data) {
    System.out.println(" Dummy Exists");
    }
}