package com.example.arcadiarest.arcadiarest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.arcadiarest.arcadiarest.models.User;
import com.example.arcadiarest.arcadiarest.services.UserService;
import com.example.arcadiarest.arcadiarest.exceptions.UserNotFoundException;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/users")
    List<User> all() {
        return userService.getUsers();
    }

    @PostMapping("/users")
    User newUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @GetMapping("/users/{id}")
    User one(@PathVariable Long id) {
        return userService.findUser(id)
            .orElseThrow(() -> new UserNotFoundException(id));
    }

    @PutMapping("/users/{id}")
    User replaceUser(@RequestBody User newUser, @PathVariable Long id) {
        return userService.findUser(id)
            .map(user -> {
                user.setUsername(newUser.getUsername());
                user.setEmail(newUser.getEmail());
                return userService.saveUser(user);
            })
            .orElseGet(() -> {
                return userService.saveUser(newUser);
            });
    }

    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
