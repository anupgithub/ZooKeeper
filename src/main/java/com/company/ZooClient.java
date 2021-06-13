package com.company;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.apache.zookeeper.CreateMode.PERSISTENT_SEQUENTIAL;

public class ZooClient extends Thread implements Watcher {

    ZooKeeper zk;
    String hostport;
    ZooClient(String host_port)
    {
        hostport = host_port;
    }

    public void run()
    {
        try {
            zk = new ZooKeeper(hostport, 600000, this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String path = "/lock";
        byte[] data = {1,2,3};
        ACL acl = new ACL(ZooDefs.Perms.ALL,ZooDefs.Ids.ANYONE_ID_UNSAFE);
        List<ACL> aclList = new ArrayList<ACL>();
        aclList.add(acl);

        CreateMode createMode = PERSISTENT_SEQUENTIAL;
        Stat stat;
        try {

            String Znode = zk.create(path, data,aclList,createMode);
            List<String> childnodes = new ArrayList<String>();
            childnodes = zk.getChildren("/",false);
            for(int i = 0; i < childnodes.size();i++)
                System.out.println("Childnode : "+ childnodes.get(i));

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Created node");

    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println("Dummy Process");
    }
}
