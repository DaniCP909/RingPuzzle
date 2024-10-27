package com.ringpuzzle.ringpuzzlerest.services;

import com.ringpuzzle.ringpuzzlerest.models.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ringpuzzle.ringpuzzlerest.dtos.LoginUserDto;
import com.ringpuzzle.ringpuzzlerest.dtos.RegisterUserDto;

@Service
public class AuthenticationService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
        UserService userservice,
        PasswordEncoder passwordEncoder,
        AuthenticationManager authenticationManager
    ) {
        this.authenticationManager = authenticationManager;
        this.userService = userservice;
        this.passwordEncoder = passwordEncoder;
    }

    public User signup(RegisterUserDto input) {
        User user = new User(input.getUsername(), input.getEmail(), passwordEncoder.encode(input.getPassword()));

        return userService.saveUser(user);
    }

    public User authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                input.getUsername(),
                input.getPassword()
            )
        );

        return userService.findByUsername(input.getUsername())
            .orElseThrow();
    }
}
