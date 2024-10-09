package com.distributed.keyvaluestore.repository;

import com.distributed.keyvaluestore.model.Node;
import com.distributed.keyvaluestore.model.User;

import java.util.Optional;

public class InMemoryRepo implements DataStoreRepo {
    private NodeResolverRepo nodeResolverRepo;


    public InMemoryRepo (NodeResolverRepo nodeResolverRepo) {
        this.nodeResolverRepo = nodeResolverRepo;
    }

    @Override
    public User createUser (User user, Node[] nodes) {
        int nodeIdx = nodeResolverRepo.getNodeIndex(user.getUserId());
        System.out.println("Storing data for user in node: " + nodeIdx);
        nodes[nodeIdx].mapOfUsers.put(nodeIdx, user);
        System.out.println("User created " + user);
        return user;
    }

    @Override
    public Optional<User> updateUser (User user, Node[] nodes) {
        Optional existingUser = getUserInfo(user.getUserId(), nodes);
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
    public Optional<User> getUser (String userId, Node[] nodes) {
        return getUserInfo(userId, nodes);
    }

    @Override
    public void deleteUser (String userId, Node[] nodes) {
        int nodeIdx = nodeResolverRepo.getNodeIndex(userId);
        if (nodes[nodeIdx].mapOfUsers.containsKey(userId)) {
            nodes[nodeIdx].mapOfUsers.remove(userId);
            System.out.println("User id : " + userId + " deleted");
        } else {
            System.out.println("User id : " + userId + " not found");
        }
    }

    private Optional<User> getUserInfo (String userId, Node[] nodes) {
        Optional optional = Optional.empty();
        int nodeIdx = nodeResolverRepo.getNodeIndex(userId);
        if (nodes[nodeIdx].mapOfUsers.containsKey(userId)) {
            System.out.println("Node id : " + nodeIdx);
            optional = Optional.of(nodes[nodeIdx].mapOfUsers.get(userId));
            System.out.println("Get User : " + nodes[nodeIdx].mapOfUsers.get(userId));
        }
        return optional;
    }
}
