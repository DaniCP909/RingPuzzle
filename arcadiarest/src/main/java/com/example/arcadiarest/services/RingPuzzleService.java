package com.example.arcadiarest.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.arcadiarest.models.RingPuzzle;
import com.example.arcadiarest.repositories.IRingPuzzleRepository;

@Service
public class RingPuzzleService {

    @Autowired
    private IRingPuzzleRepository ringPuzzleRepository;

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

    public RingPuzzle findByAccessionNumber(String accesionNumber) {
        return ringPuzzleRepository.findByAccessionNumber(accesionNumber).get();
    }

}
