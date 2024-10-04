package com.distributed.keyvaluestore.initialCode;

import java.util.HashMap;

public class Node {
    private HashMap<String, String> hashMap;
    private final int nodeId;

    public Node (int nodeId) {
        this.hashMap = new HashMap<>();
        this.nodeId = nodeId;
    }

    public synchronized void addData (String key, String value) {
        hashMap.put(key, value);
        System.out.println("Data added to Node " + nodeId + ": { " + key + ": " + value + " }");
    }

    public synchronized void displayHashMap () {
        System.out.println("Node " + nodeId + " HashMap: " + hashMap);
    }
}
