package com.example.arcadiarest.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.arcadiarest.models.User;
import com.example.arcadiarest.repositories.IUserRepository;

@Service
public class UserService {

    @Autowired
    IUserRepository userRepository;

    public List<User> getUsers() {
        return (List<User>) userRepository.findAll();
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> findUser(Long id) {
        return userRepository.findById(id);
    }

    public void replaceUser(User user) {
        userRepository.findById(user.getId()).orElseThrow();
        userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

}
