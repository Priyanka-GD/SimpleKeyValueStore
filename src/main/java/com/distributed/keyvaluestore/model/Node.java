package com.distributed.keyvaluestore.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Node {
    private int nodeId;
    public Map<String, User> mapOfUsers;

    public Node (int nodeId) {
        this.nodeId = nodeId;
        this.mapOfUsers = new HashMap<>();
    }

    public synchronized void put (String userId, User user) {
        this.mapOfUsers.put(userId, user);
    }

    public synchronized Optional get (String userId) {
        Optional optional = Optional.empty();
        if (this.mapOfUsers.containsKey(userId)) {
            optional = Optional.of(this.mapOfUsers.get(userId));
        }
        return optional;
    }
}
