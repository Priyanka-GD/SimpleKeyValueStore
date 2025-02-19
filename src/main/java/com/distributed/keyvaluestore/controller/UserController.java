package com.distributed.keyvaluestore.controller;

import com.distributed.keyvaluestore.demo.Master;
import com.distributed.keyvaluestore.model.User;
import com.distributed.keyvaluestore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController (UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public User getUser (@PathVariable String id) {
        Optional user = userService.getUser(id);
        if (user.isPresent()) {
            return (User) user.get();
        }
        return null;
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public User addUser (@RequestBody User newUser) {
        return userService.addUser(newUser);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<User> updateUser (@PathVariable String id, @RequestBody User updatedUser) {
        Optional existingUser = userService.getUser(id);
        if (existingUser.isPresent()) {
            User user = (User) existingUser.get();
            user.setName(updatedUser.getName());
            user.setEmail(updatedUser.getEmail());
            user.setAddress(updatedUser.getAddress());
            return userService.updateUser(user);
        }
        return Optional.empty();
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser (@PathVariable String id) {
        userService.deleteUser(id);
    }
}
