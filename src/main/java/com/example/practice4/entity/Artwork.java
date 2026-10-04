package com.example.practice4.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "artworks")
public class Artwork {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(name = "created_year")
    private Integer createdYear;

    @Column(precision = 12, scale = 2)
    private BigDecimal price;

    @Column(length = 60)
    private String style;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "artist_id", nullable = false)
    private Artist artist;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    protected Artwork() { }

    public Artwork(String title, Integer createdYear, BigDecimal price, String style, Artist artist) {
        this.title = title;
        this.createdYear = createdYear;
        this.price = price;
        this.style = style;
        this.artist = artist;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public Integer getCreatedYear() { return createdYear; }
    public void setCreatedYear(Integer createdYear) { this.createdYear = createdYear; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public String getStyle() { return style; }
    public void setStyle(String style) { this.style = style; }
    public Artist getArtist() { return artist; }
    public void setArtist(Artist artist) { this.artist = artist; }
    public Instant getCreatedAt() { return createdAt; }
}