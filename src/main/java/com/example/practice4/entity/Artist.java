package com.example.practice4.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "artists")
public class Artist {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 120)
    private String name;
    @Column(length = 60)
    private String country;
    @Column(name = "birth_year")
    private Integer birthYear;
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt = Instant.now();
    protected Artist() { }
    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public Integer getBirthYear() {
        return birthYear;
    }
    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }
    public Instant getCreatedAt() {
        return createdAt;
    }
    public Artist(String name, String country, Integer birthYear) { 
        this.name = name;
        this.country = country;
        this.birthYear = birthYear;
    }
}
