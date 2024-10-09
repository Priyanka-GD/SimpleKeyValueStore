package com.distributed.keyvaluestore.repository;

import com.distributed.keyvaluestore.model.Node;
import com.distributed.keyvaluestore.model.User;

import java.util.Optional;

public interface DataStoreRepo {
    User createUser (User user,  Node[] nodes);

    Optional<User>  updateUser (User user, Node[] nodes);

    Optional<User> getUser (String userId, Node[] nodes);

    void deleteUser (String userId,  Node[] nodes);
}
