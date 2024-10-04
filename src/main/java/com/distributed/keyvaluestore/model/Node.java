package com.distributed.keyvaluestore.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Node {
    private int nodeId;
    public Map<Integer, User> mapOfUsers;

    public Node (int nodeId) {
        this.nodeId = nodeId;
        this.mapOfUsers = new HashMap<>();
    }

    public synchronized void put (int nodeId, User user) {
        this.mapOfUsers.put(nodeId, user);
    }

    public synchronized Optional get (int userId) {
        Optional optional = Optional.empty();
        if (this.mapOfUsers.containsKey(userId)) {
            optional = Optional.of(this.mapOfUsers.get(userId));
        }
        return optional;
    }
}
