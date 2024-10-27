package com.ringpuzzle.ringpuzzlerest.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ringpuzzle.ringpuzzlerest.models.RingPuzzle;
import com.ringpuzzle.ringpuzzlerest.models.SolvedBy;
import com.ringpuzzle.ringpuzzlerest.models.User;

@Repository
public interface ISolvedByRepository extends JpaRepository<SolvedBy, Long>{
    public List<SolvedBy> findBySolvedRingPuzzle(RingPuzzle ringPuzzle);
    public List<SolvedBy> findByUser(User user);
    public Optional<SolvedBy> findByUserAndSolvedRingPuzzle(User user, RingPuzzle ringPuzzle);
}
