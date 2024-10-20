package com.example.arcadiarest.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.arcadiarest.models.RingPuzzle;
import com.example.arcadiarest.models.SolvedBy;
import com.example.arcadiarest.models.User;

@Repository
public interface ISolvedByRepository extends JpaRepository<SolvedBy, Long>{
    public List<SolvedBy> findBySolvedRingPuzzle(RingPuzzle ringPuzzle);
    public List<SolvedBy> findByUser(User user);
    public Optional<SolvedBy> findByUserAndSolvedRingPuzzle(User user, RingPuzzle ringPuzzle);
}
