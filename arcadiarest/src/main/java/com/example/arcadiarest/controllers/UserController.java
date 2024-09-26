package com.example.arcadiarest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.arcadiarest.models.User;
import com.example.arcadiarest.services.UserService;

@RequestMapping("/users")
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/me")
    public ResponseEntity<User> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        User currentUser = (User) authentication.getPrincipal();

        return ResponseEntity.ok(currentUser);
    }

    @GetMapping("/")
    public ResponseEntity<List<User>> allUsers() {
        List <User> users = userService.getUsers();

        return ResponseEntity.ok(users);
    }

    //@PostMapping("/users")
    //User newUser(@RequestBody User user) {
    //    return userService.saveUser(user);
    //}
//
    //@GetMapping("/users/{id}")
    //User one(@PathVariable Long id) {
    //    return userService.findUser(id)
    //        .orElseThrow(() -> new UserNotFoundException(id));
    //}
//
    //@PutMapping("/users/{id}")
    //User replaceUser(@RequestBody User newUser, @PathVariable Long id) {
    //    return userService.findUser(id)
    //        .map(user -> {
    //            user.setUsername(newUser.getUsername());
    //            user.setEmail(newUser.getEmail());
    //            return userService.saveUser(user);
    //        })
    //        .orElseGet(() -> {
    //            return userService.saveUser(newUser);
    //        });
    //}
//
    //@DeleteMapping("/users/{id}")
    //void deleteUser(@PathVariable Long id) {
    //    userService.deleteUser(id);
    //}
}
