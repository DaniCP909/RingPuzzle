package com.example.arcadiarest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.arcadiarest.models.RingPuzzle;
import com.example.arcadiarest.models.SolvedBy;
import com.example.arcadiarest.models.User;
import com.example.arcadiarest.services.RingPuzzleService;
import com.example.arcadiarest.services.SolvedByService;

@RestController
public class RingPuzzleController {

    @Autowired
    private RingPuzzleService ringPuzzleService;

    @Autowired
    private SolvedByService solvedByService;


    @GetMapping("/ringpuzzle/{accessionNumber}")
    public ResponseEntity<SolvedBy> getRingPuzzleByAN(@PathVariable String accessionNumber) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if(authentication != null && authentication.getPrincipal() instanceof User) {
            User currentUser = (User) authentication.getPrincipal();
            RingPuzzle ringPuzzle = ringPuzzleService.findByAccessionNumber(accessionNumber);
            SolvedBy solvedBy = solvedByService.findByUserAndSolvedRingPuzzle(currentUser, ringPuzzle);
            return ResponseEntity.ok(solvedBy);
        }
        else {
            return ResponseEntity.status(200).build();
        }

    }

    @GetMapping("/ringpuzzles/")
    public ResponseEntity<List<RingPuzzle>> allRingPuzzles() {
        List<RingPuzzle> rings = ringPuzzleService.findAll();
        return ResponseEntity.ok(rings);
    }

    @PostMapping("/ringpuzzles/{accessionNumber}/edit")
    public ResponseEntity<SolvedBy> editRingPuzzle(@PathVariable String accessionNumber, @RequestBody SolvedBy revision) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        RingPuzzle ringPuzzle = ringPuzzleService.findByAccessionNumber(accessionNumber);
        SolvedBy solvedBy = solvedByService.findByUserAndSolvedRingPuzzle(currentUser, ringPuzzle);

        solvedBy.setScalesSolved(revision.getScalesSolved());
        solvedBy.setDifficultyLevels(revision.getDifficultyLevels());
        solvedBy.setSolvedAt(revision.getSolvedAt());
        solvedBy.setRingsPosition(revision.getRingsPosition());

        return ResponseEntity.ok(solvedByService.save(solvedBy));
    }

    @PostMapping("/ringpuzzles/create")
    public ResponseEntity<RingPuzzle> createPuzzle(@RequestBody RingPuzzle ringPuzzle) {
        RingPuzzle newRP = new RingPuzzle(
            ringPuzzle.getAccessionNumber(),
            ringPuzzle.getTitle(),
            ringPuzzle.getAuthor(),
            ringPuzzle.getMinDate(),
            ringPuzzle.getMaxDate(),
            ringPuzzle.getDescription()
        );

        return ResponseEntity.ok(ringPuzzleService.saveRingPuzzle(newRP));


    }

    @GetMapping("/ringpuzzles/{accessionNumber}/users")
    public ResponseEntity<List<SolvedBy>> getUsersSolvedRingPuzzle(@PathVariable String accessionNumber) {
        RingPuzzle ringPuzzle = ringPuzzleService.findByAccessionNumber(accessionNumber);
        return ResponseEntity.ok(this.solvedByService.findBySolvedRingPuzzle(ringPuzzle));
    }

}
