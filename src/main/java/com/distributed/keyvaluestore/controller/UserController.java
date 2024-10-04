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
    public User getUser (@PathVariable int id) {
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

}
