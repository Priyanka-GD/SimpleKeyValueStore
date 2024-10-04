package com.distributed.keyvaluestore.service;

import com.distributed.keyvaluestore.model.Node;
import com.distributed.keyvaluestore.model.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private static final int COUNT_OF_NODES = 10;
    private Node[] nodes;

    public UserService () {
        nodes = new Node[COUNT_OF_NODES];
        for (int node = 0; node < COUNT_OF_NODES; node++) {
            nodes[node] = new Node(node);
        }
    }

    public Optional<User> getUser (Integer id) {
        Optional optional = Optional.empty();
        int nodeId = id % COUNT_OF_NODES;
        System.out.println("Node id : " + nodeId);
        if (nodes[nodeId].mapOfUsers.containsKey(id))
            optional = Optional.of(nodes[nodeId].mapOfUsers.get(id));
        return optional;
    }

    public User addUser (User newUser) {
        int userId = newUser.getUserId();
        int nodeIdx = userId % COUNT_OF_NODES;
        nodes[nodeIdx].put(userId, newUser);
        System.out.println("User Created :" + userId);
        return newUser;
    }
}
