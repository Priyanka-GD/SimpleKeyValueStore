package com.distributed.keyvaluestore.service;
import com.distributed.keyvaluestore.model.User;
import com.distributed.keyvaluestore.repository.DataStoreRepo;
import org.springframework.stereotype.Service;
import java.util.Optional;


@Service
public class UserService {
    private DataStoreRepo dataStoreRepo;



    public UserService (DataStoreRepo dataStoreRepo) {

        this.dataStoreRepo = dataStoreRepo;
    }

    public Optional<User> getUser (String userId) {
        return dataStoreRepo.getUser(userId);
    }

    public User addUser (User newUser) {
        return dataStoreRepo.createUser(newUser);
    }

    public void deleteUser (String userId) {
        dataStoreRepo.deleteUser(userId);
    }

    public Optional<User> updateUser (User updateUser) {
        return dataStoreRepo.updateUser(updateUser);
    }

}
