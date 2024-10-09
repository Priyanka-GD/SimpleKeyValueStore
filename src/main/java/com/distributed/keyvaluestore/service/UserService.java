package com.distributed.keyvaluestore.service;

import com.distributed.keyvaluestore.CommonVariables;
import com.distributed.keyvaluestore.model.Node;
import com.distributed.keyvaluestore.model.User;
import com.distributed.keyvaluestore.repository.DataStoreRepo;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.distributed.keyvaluestore.CommonVariables.COUNT_OF_NODES;

@Service
public class UserService {
    private DataStoreRepo dataStoreRepo;

    private Node[] nodes;

    public UserService () {
        nodes = new Node[COUNT_OF_NODES];
        for (int node = 0; node < COUNT_OF_NODES; node++) {
            nodes[node] = new Node(node);
        }
    }

    public Optional<User> getUser (String userId) {
        return dataStoreRepo.getUser(userId, nodes);
    }

    public User addUser (User newUser) {
        return dataStoreRepo.createUser(newUser, nodes);
    }

    public void deleteUser (String userId) {
        dataStoreRepo.deleteUser(userId, nodes);
    }

    public Optional<User>  updateUser (User updateUser) {
        dataStoreRepo.updateUser(updateUser, nodes);
    }

}
