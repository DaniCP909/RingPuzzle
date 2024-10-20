package com.example.arcadiarest.config;

import com.example.arcadiarest.models.RingPuzzle;
import com.example.arcadiarest.models.SolvedBy;
import com.example.arcadiarest.models.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.arcadiarest.services.RingPuzzleService;
import com.example.arcadiarest.services.SolvedByService;
import com.example.arcadiarest.services.UserService;

@Configuration
public class DBInit {

    private static final Logger log = LoggerFactory.getLogger(DBInit.class);

    private UserService userService;
    private RingPuzzleService ringPuzzleService;
    private SolvedByService solvedByService;

    private BCryptPasswordEncoder passwordEncoder;

    public DBInit(
        UserService userService, 
        RingPuzzleService ringPuzzleService,
        SolvedByService solvedByService,
        BCryptPasswordEncoder passwordEncoder
        ) {
            this.userService = userService;
            this.ringPuzzleService = ringPuzzleService;
            this.passwordEncoder = passwordEncoder;
            this.solvedByService = solvedByService;
        }

    @Bean
    CommandLineRunner initDatabase() {

        RingPuzzle rp0 = new RingPuzzle(
            "0-0-0",
            "La rendicion de Breda",
            "Velazquez",
            1635,
            1635,
            "Óleo sobre lienzo, 307,3 x 371,5 cm" 
            );

        RingPuzzle rp1 = new RingPuzzle(
            "0-0-1",
            "Bodegón con flores, copa de plata dorada, almendras, frutos secos, dulces, panecillos, vino y jarra de peltre",
            "Clara Peeters", 
            1611,
            1611,
            "Óleo sobre tabla, 52 x 73 cm"
            );

        User u0 = new User("Booker", "booker@gmail.com", passwordEncoder.encode("Password123?"));
        User u1 = new User("Neo", "neo@gmail.com", passwordEncoder.encode("Password123?"));

        SolvedBy sb0 = new SolvedBy(rp0, u0);
        SolvedBy sb01 = new SolvedBy(rp1, u0);
        SolvedBy sb1 = new SolvedBy(rp1, u1);

        return args -> {
            log.info("Preloading " + userService.saveUser(u0));
            log.info("Preloading " + userService.saveUser(u1));

            log.info("Preloading " + ringPuzzleService.saveRingPuzzle(rp0));

            log.info("Preloading " + ringPuzzleService.saveRingPuzzle(rp1));

            log.info("Preloading " + solvedByService.save(sb0));
            log.info("Preloading " + solvedByService.save(sb1));
            log.info("Preloading " + solvedByService.save(sb01));

        };
    }

}
