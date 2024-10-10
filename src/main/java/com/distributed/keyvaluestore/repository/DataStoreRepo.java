package com.distributed.keyvaluestore.repository;

import com.distributed.keyvaluestore.model.Node;
import com.distributed.keyvaluestore.model.User;

import java.util.Optional;

public interface DataStoreRepo {
    User createUser (User user);

    Optional<User>  updateUser (User user);

    Optional<User> getUser (String userId);

    void deleteUser (String userId);
}
