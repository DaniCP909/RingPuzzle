package com.example.arcadiarest.arcadiarest.config;

import com.example.arcadiarest.arcadiarest.models.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.arcadiarest.arcadiarest.services.UserService;

@Configuration
public class DBInit {

    private static final Logger log = LoggerFactory.getLogger(DBInit.class);

    @Autowired
    UserService userService;

    @Bean
    CommandLineRunner initDatabase() {
        return args -> {
            log.info("Preloading " + userService.saveUser(new User("Booker", "booker@gmail.com")));
            log.info("Preloading " + userService.saveUser(new User("Neo", "neo@gmail.com")));
        };
    }

}
