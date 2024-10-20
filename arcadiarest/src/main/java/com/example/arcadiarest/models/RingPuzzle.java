package com.example.arcadiarest.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Table(name = "ring_puzzles")
@Entity
public class RingPuzzle {

    private static final int MIN_RINGS = 4;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String accessionNumber;

    @Column(nullable = false, unique = true)
    private String title;


    @Column(nullable = false)
    private int scalesCreated;

    @Column(length = 100, nullable = false)
    private String author;

    @Column(nullable = false)
    private int minDate;

    @Column(nullable = false)
    private int maxDate;

    @Column(length = 400, nullable = false)
    private String description;

    @OneToMany(mappedBy = "solvedRingPuzzle")
    @JsonIgnore
    private List<SolvedBy> solvedBy;

    public RingPuzzle() {}

    public RingPuzzle(String accessionNumber, String title, String author, int minDate, int maxDate, String description) {
        this.accessionNumber = accessionNumber;
        this.title = title;
        this.author = author;
        this.minDate = minDate;
        this.maxDate = maxDate;
        this.description = description;
        this.scalesCreated = MIN_RINGS - 1; //Init at 3, will +1 at when a group af RINGS is created in resources (4, 5, 6, 7 ...)
                                            //each scale corresponds to the number of RINGS
    }

    public String getAccessionNumber() {
        return this.accessionNumber;
    }

    public void setAccesionNumber(String accessionNumber) {
        this.accessionNumber = accessionNumber;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getScalesCreated() {
        return scalesCreated;
    }

    public void setScalesCreated(int scalesCreated) {
        this.scalesCreated = scalesCreated;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getMinDate() {
        return this.minDate;
    }

    public void setMinDate(int minDate) {
        this.minDate = minDate;
    }

    public int getMaxDate() {
        return this.maxDate;
    }

    public void setMaxDate(int maxDate) {
        this.maxDate = maxDate;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<SolvedBy> getSolvedBy() {
        return this.solvedBy;
    }

    public void setSolvedBy(List<SolvedBy> solvedBy) {
        this.solvedBy = solvedBy;
    }

    public static int getMinRings() {
        return RingPuzzle.MIN_RINGS;
    }

    public void increaseScalesCreated() {
        this.scalesCreated++;
    }

    @Override
    public String toString() {
        return "RingPuzzle{" + "id=" + this.id + ", title=" + this.title + ", author=" + this.author + "...";
    }

}
