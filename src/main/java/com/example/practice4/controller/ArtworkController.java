package com.example.practice4.controller;

import com.example.practice4.dto.*;
import com.example.practice4.service.ArtworkService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/artworks")
public class ArtworkController {

    private final ArtworkService artworkService;

    public ArtworkController(ArtworkService artworkService) {
        this.artworkService = artworkService;
    }

    @GetMapping
    public PageResponse<ArtworkDto> list(
            @RequestParam(required = false) String style,
            @RequestParam(required = false) String text,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        return artworkService.find(style, text, page, size, sortBy, direction);
    }

    @GetMapping("/{id}")
    public ArtworkDto get(@PathVariable Long id) {
        return artworkService.get(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ArtworkDto create(@Valid @RequestBody CreateArtworkRequest request) {
        return artworkService.create(request);
    }
}