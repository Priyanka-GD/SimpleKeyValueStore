package com.distributed.keyvaluestore.demo;

import java.util.*;

public class Node {
    private int id;
    private LRUCache cache;

    public Node(int id) {
        this.id = id;
        this.cache = new LRUCache();
    }

    public int getId() {
        return this.id;
    }

    public LRUCache getCache() {
        return this.cache;
    }
}
