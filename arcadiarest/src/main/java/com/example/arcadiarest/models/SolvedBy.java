package com.example.arcadiarest.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class SolvedBy {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ring_puzzle_id")
    @JsonIgnore
    private RingPuzzle solvedRingPuzzle;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "rings_position", joinColumns = @JoinColumn(name = "solved_by_id"))
    private List<Double> ringsPosition;

    //Number of Rings solved for this puzzle: 0 -> not solved, 4 -> solved first level by N_RINGS difficulty
    private int scalesSolved;

    //Difficulty level for this puzzle with (scales_solved - 1) Rings
    private int difficultyLevels;

    private LocalDateTime solvedAt;

    public SolvedBy() {}

    public SolvedBy(RingPuzzle ringPuzzle, User user) {
        this.solvedRingPuzzle = ringPuzzle;
        this.user = user;
        this.scalesSolved = 0;
        this.difficultyLevels = 0;
        this.solvedAt = null;
        this.ringsPosition = new ArrayList<Double>();
        for(int i = 0; i < RingPuzzle.getMinRings(); i++) {
            this.ringsPosition.add(0.0);
        }
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RingPuzzle getRingPuzzle() {
        return this.solvedRingPuzzle;
    }

    public void setRingPuzzle(RingPuzzle ringPuzzle) {
        this.solvedRingPuzzle = ringPuzzle;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getScalesSolved() {
        return this.scalesSolved;
    }

    public void setScalesSolved(int scalesSolved) {
        this.scalesSolved = scalesSolved;
    }

    public int getDifficultyLevels() {
        return this.difficultyLevels;
    }

    public void setDifficultyLevels(int difficultyLevels) {
        this.difficultyLevels = difficultyLevels;
    }

    public LocalDateTime getSolvedAt() {
        return this.solvedAt;
    }

    public void setSolvedAt(LocalDateTime solvedAt) {
        this.solvedAt = solvedAt;
    }

    public List<Double> getRingsPosition() {
        return this.ringsPosition;
    }

    public void setRingsPosition(List<Double> ringsPosition) {
        this.ringsPosition = ringsPosition;
    }

    @Override
    public String toString() {
        return "SolvedBy{" + "id=" + this.id + ", scalesSolved=" + this.scalesSolved + ", difficultyLevels=" + this.difficultyLevels;
    }

}
