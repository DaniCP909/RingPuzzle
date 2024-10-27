package com.ringpuzzle.ringpuzzlerest.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ringpuzzle.ringpuzzlerest.models.RingPuzzle;
import com.ringpuzzle.ringpuzzlerest.models.SolvedBy;
import com.ringpuzzle.ringpuzzlerest.models.User;
import com.ringpuzzle.ringpuzzlerest.repositories.ISolvedByRepository;

@Service
public class SolvedByService {

    @Autowired
    ISolvedByRepository solvedByRepository;

    public List<SolvedBy> allSolvedRecords() {
        return this.solvedByRepository.findAll();
    }

    public SolvedBy save(SolvedBy solvedBy) {
        return this.solvedByRepository.save(solvedBy);
    }

    public List<SolvedBy> findBySolvedRingPuzzle(RingPuzzle ringPuzzle) {
        return this.solvedByRepository.findBySolvedRingPuzzle(ringPuzzle);
    }

    public List<SolvedBy> findByUser(User user) {
        return this.solvedByRepository.findByUser(user);
    }

    public SolvedBy findByUserAndSolvedRingPuzzle(User user, RingPuzzle ringPuzzle) {
        Optional<SolvedBy> solvedBy = this.solvedByRepository.findByUserAndSolvedRingPuzzle(user, ringPuzzle);
        if(solvedBy.isPresent()) {
            return solvedBy.get();
        }
        else {
            return null;
        }
    }
}
