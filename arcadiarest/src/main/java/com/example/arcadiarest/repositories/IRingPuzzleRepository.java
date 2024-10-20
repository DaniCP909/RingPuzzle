package com.example.arcadiarest.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.arcadiarest.models.RingPuzzle;

@Repository
public interface IRingPuzzleRepository extends JpaRepository<RingPuzzle, Long> {
    Optional<RingPuzzle> findByAccessionNumber(String accessionNumber);
}
