package com.ringpuzzle.ringpuzzlerest.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ringpuzzle.ringpuzzlerest.models.RingPuzzle;
import com.ringpuzzle.ringpuzzlerest.models.User;

@Repository
public interface IRingPuzzleRepository extends JpaRepository<RingPuzzle, Long> {
    Optional<RingPuzzle> findByAccessionNumber(String accessionNumber);
    void deleteByAccessionNumber(String accessionNumber);

    @Query("SELECT rp FROM RingPuzzle rp WHERE NOT EXISTS ("
         + "SELECT 1 FROM SolvedBy sb WHERE sb.solvedRingPuzzle = rp AND sb.user = :user) "
         + "ORDER BY function('RAND')")
    public List<RingPuzzle> findRandomUnsolvedRPByUser(@Param("user") User user, Pageable pageable);
}
