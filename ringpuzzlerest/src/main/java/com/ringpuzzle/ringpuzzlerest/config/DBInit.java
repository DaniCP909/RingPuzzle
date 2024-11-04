package com.ringpuzzle.ringpuzzlerest.config;

import com.ringpuzzle.ringpuzzlerest.models.RingPuzzle;
import com.ringpuzzle.ringpuzzlerest.models.SolvedBy;
import com.ringpuzzle.ringpuzzlerest.models.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ringpuzzle.ringpuzzlerest.services.RingPuzzleService;
import com.ringpuzzle.ringpuzzlerest.services.SolvedByService;
import com.ringpuzzle.ringpuzzlerest.services.UserService;

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
        RingPuzzle rp2 = new RingPuzzle(
            "0-0-2",
            "La rendicion de Breda 2",
            "Velazquez",
            1635,
            1635,
            "Óleo sobre lienzo, 307,3 x 371,5 cm" 
            );
    
        RingPuzzle rp3 = new RingPuzzle(
            "0-0-3",
            "Bodegón con flores, copa de plata dorada, almendras, frutos secos, dulces, panecillos, vino y jarra de peltre 2",
            "Clara Peeters", 
            1611,
            1611,
            "Óleo sobre tabla, 52 x 73 cm"
            );

        User u0 = new User("Booker", "booker@gmail.com", passwordEncoder.encode("Password123?"));
        User u1 = new User("Neo", "neo@gmail.com", passwordEncoder.encode("Password123?"));
        User uguest = new User("guest", "guest@gmail.com", passwordEncoder.encode("Password123?"));

        SolvedBy sb0 = new SolvedBy(rp0, u0);
        SolvedBy sb01 = new SolvedBy(rp1, u0);
        SolvedBy sb1 = new SolvedBy(rp1, u1);

        return args -> {
            log.info("Preloading " + userService.saveUser(u0));
            log.info("Preloading " + userService.saveUser(u1));
            log.info("Preloading " + userService.saveUser(uguest));

            log.info("Preloading " + ringPuzzleService.saveRingPuzzle(rp0));
            log.info("Preloading " + ringPuzzleService.saveRingPuzzle(rp1));
            //log.info("Preloading " + ringPuzzleService.saveRingPuzzle(rp2));
            //log.info("Preloading " + ringPuzzleService.saveRingPuzzle(rp3));

            log.info("Preloading " + solvedByService.save(sb0));
            log.info("Preloading " + solvedByService.save(sb1));
            log.info("Preloading " + solvedByService.save(sb01));

        };
    }

}
