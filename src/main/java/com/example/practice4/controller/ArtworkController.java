package com.example.practice4.controller;

import com.example.practice4.dto.*;
import com.example.practice4.service.ArtworkService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/artworks")
@Tag(name = "Artworks", description = "Artworks catalog")
public class ArtworkController {

    private final ArtworkService artworkService;

    public ArtworkController(ArtworkService artworkService) {
        this.artworkService = artworkService;
    }
    @Operation(summary = "List artworks",
               description = "Paged list; filter by style or title text. Result is cached for 60 s.")
    @ApiResponse(responseCode = "200", description = "Page of artworks")
    @GetMapping
    public PageResponse<ArtworkDto> list(
            @Parameter(description = "Filter by style, e.g. Cubism") @RequestParam(required = false) String style,
            @Parameter(description = "Search in title") @RequestParam(required = false) String text,
            @Parameter(description = "Page number, starts at 0") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size, max 50") @RequestParam(defaultValue = "5") int size,
            @Parameter(description = "id, title, createdYear, price or style") @RequestParam(defaultValue = "id") String sortBy,
            @Parameter(description = "asc or desc") @RequestParam(defaultValue = "asc") String direction) {
        return artworkService.find(style, text, page, size, sortBy, direction);
    }

    @GetMapping("/{id}")
    public ArtworkDto get(@PathVariable Long id) {
        return artworkService.get(id);
    }
    @Operation(summary = "Create artwork")
    @ApiResponse(responseCode = "201", description = "Created")
    @ApiResponse(responseCode = "400", description = "Validation error")
    @ApiResponse(responseCode = "404", description = "Artist not found")

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ArtworkDto create(@Valid @RequestBody CreateArtworkRequest request) {
        return artworkService.create(request);
    }
}