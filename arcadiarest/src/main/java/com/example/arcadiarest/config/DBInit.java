package com.example.arcadiarest.config;

import com.example.arcadiarest.models.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.arcadiarest.services.UserService;

@Configuration
public class DBInit {

    private static final Logger log = LoggerFactory.getLogger(DBInit.class);

    private UserService userService;
    private BCryptPasswordEncoder passwordEncoder;

    public DBInit(UserService userService, 
                    BCryptPasswordEncoder passwordEncoder) {
                        this.userService = userService;
                        this.passwordEncoder = passwordEncoder;
                    }

    @Bean
    CommandLineRunner initDatabase() {
        return args -> {
            log.info("Preloading " + userService.saveUser(new User("Booker", "booker@gmail.com", passwordEncoder.encode("Password123?"))));
            log.info("Preloading " + userService.saveUser(new User("Neo", "neo@gmail.com", passwordEncoder.encode("Password123?"))));
        };
    }

}
