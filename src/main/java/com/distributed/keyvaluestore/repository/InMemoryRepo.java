package com.distributed.keyvaluestore.repository;

import com.distributed.keyvaluestore.model.Node;
import com.distributed.keyvaluestore.model.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.distributed.keyvaluestore.CommonVariables.COUNT_OF_NODES;

@Repository
public class InMemoryRepo implements DataStoreRepo {
    private NodeResolverRepo nodeResolverRepo;
    private Node[] nodes;

    public InMemoryRepo (NodeResolverRepo nodeResolverRepo) {
        this.nodeResolverRepo = nodeResolverRepo;
        nodes = new Node[COUNT_OF_NODES];
        for (int node = 0; node < COUNT_OF_NODES; node++) {
            nodes[node] = new Node(node);
        }
    }

    @Override
    public User createUser (User user) {
        int nodeIdx = nodeResolverRepo.getNodeIndex(user.getUserId());
        System.out.println("Storing data for user in node: " + nodeIdx);
        nodes[nodeIdx].mapOfUsers.put(user.getUserId(), user);
        System.out.println("User created " + user.getUserId());
        return user;
    }

    @Override
    public Optional<User> updateUser (User user) {
        Optional existingUser = getUserInfo(user.getUserId());
        if (existingUser.isPresent()) {
            User updatedUser = (User) existingUser.get();
            user.setName(updatedUser.getName());
            user.setEmail(updatedUser.getEmail());
            user.setAddress(updatedUser.getAddress());
            System.out.println("User id : " + user.getUserId() + " updated");
            return existingUser;
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> getUser (String userId) {
        return getUserInfo(userId);
    }

    @Override
    public void deleteUser (String userId) {
        int nodeIdx = nodeResolverRepo.getNodeIndex(userId);
        if (nodes[nodeIdx].mapOfUsers.containsKey(userId)) {
            nodes[nodeIdx].mapOfUsers.remove(userId);
            System.out.println("User id : " + userId + " deleted");
        } else {
            System.out.println("User id : " + userId + " not found");
        }
    }

    private Optional<User> getUserInfo (String userId) {
        Optional optional = Optional.empty();
        int nodeIdx = nodeResolverRepo.getNodeIndex(userId);
        if (nodes[nodeIdx].mapOfUsers.containsKey(userId)) {
            System.out.println("Node id : " + nodeIdx);
            optional = Optional.of(nodes[nodeIdx].mapOfUsers.get(userId));
            System.out.println("Get User : " + nodes[nodeIdx].mapOfUsers.get(userId).getUserId());
        }
        return optional;
    }
}
