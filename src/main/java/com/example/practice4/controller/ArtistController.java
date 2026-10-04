package com.example.practice4.controller;

import com.example.practice4.dto.*;
import com.example.practice4.service.ArtistService;
import com.example.practice4.service.ArtworkService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/artists")
public class ArtistController {

    private final ArtistService artistService;
    private final ArtworkService artworkService;

    public ArtistController(ArtistService artistService, ArtworkService artworkService) {
        this.artistService = artistService;
        this.artworkService = artworkService;
    }

    @GetMapping
    public PageResponse<ArtistDto> list(
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String text,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        return artistService.find(country, text, page, size, sortBy, direction);
    }

    @GetMapping("/{id}")
    public ArtistDto get(@PathVariable Long id) {
        return artistService.get(id);
    }

    @GetMapping("/{id}/artworks")
    public PageResponse<ArtworkDto> artworks(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        return artworkService.findByArtist(id, page, size, sortBy, direction);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ArtistDto create(@Valid @RequestBody CreateArtistRequest request) {
        return artistService.create(request);
    }
}