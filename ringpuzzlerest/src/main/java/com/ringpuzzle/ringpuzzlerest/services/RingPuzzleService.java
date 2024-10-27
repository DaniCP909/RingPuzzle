package com.ringpuzzle.ringpuzzlerest.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ringpuzzle.ringpuzzlerest.models.RingPuzzle;
import com.ringpuzzle.ringpuzzlerest.models.SolvedBy;
import com.ringpuzzle.ringpuzzlerest.models.User;
import com.ringpuzzle.ringpuzzlerest.repositories.IRingPuzzleRepository;

@Service
public class RingPuzzleService {

    @Autowired
    private IRingPuzzleRepository ringPuzzleRepository;

    @Autowired
    private SolvedByService solvedByService;

    public List<RingPuzzle> findAll() {
        return ringPuzzleRepository.findAll();
    }

    public List<RingPuzzle> findAllById(List<Long> ids) {
        return ringPuzzleRepository.findAllById(ids);
    }

    public RingPuzzle findById(Long id) {
        return ringPuzzleRepository.findAllById(Arrays.asList(id)).get(0);
    }

    public RingPuzzle saveRingPuzzle(RingPuzzle ringPuzzle) {
        return ringPuzzleRepository.save(ringPuzzle);
    }

    public void deleteRingPuzzle(Long id) {
        ringPuzzleRepository.deleteById(id);
    }

    public void deleteByAccessionNumber(String accessionNumber) {
        ringPuzzleRepository.deleteByAccessionNumber(accessionNumber);
    }

    public RingPuzzle findByAccessionNumber(String accessionNumber) {
        return ringPuzzleRepository.findByAccessionNumber(accessionNumber)
        .orElseThrow(()-> new RuntimeException("RingPuzzle not found with accession number: " + accessionNumber));
    }

    public SolvedBy getRandomUnsolvedRPByUser(User user) {
        Pageable pageable = PageRequest.of(0,1);
        if(ringPuzzleRepository.findRandomUnsolvedRPByUser(user, pageable).size() == 0) return null;

        RingPuzzle ringPuzzle = ringPuzzleRepository.findRandomUnsolvedRPByUser(user, pageable).get(0);
        SolvedBy newSolvedByRecord = new SolvedBy(ringPuzzle, user);
        return solvedByService.save(newSolvedByRecord);
    }

}
