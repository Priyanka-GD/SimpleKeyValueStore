package com.distributed.keyvaluestore.controller;

import com.distributed.keyvaluestore.model.User;
import com.distributed.keyvaluestore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class UserController {
    private UserService userService;

    @Autowired
    public UserController (UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/{id}")
    public User getUser (@PathVariable String id) {
        Optional user = userService.getUser(id);
        if (user.isPresent()) {
            return (User) user.get();
        }
        return null;
    }

    @PostMapping("/user/add")
    @ResponseStatus(HttpStatus.CREATED)
    public User addUser (@RequestBody User newUser) {
        return userService.addUser(newUser);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User updateUser (@PathVariable String id, @RequestBody User updatedUser) {
        Optional existingUser = userService.getUser(id);
        if (existingUser.isPresent()) {
            User user = (User) existingUser.get();
            user.setName(updatedUser.getName());
            user.setEmail(updatedUser.getEmail());
            user.setAddress(updatedUser.getAddress());
        }
        return userService.addUser(updatedUser);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser (@PathVariable String id) {
        userService.deleteUser(id);
    }

}
