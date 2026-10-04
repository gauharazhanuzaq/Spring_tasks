package com.example.practice4.controller;

import com.example.practice4.dto.*;
import com.example.practice4.service.ExhibitionService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/exhibitions")
public class ExhibitionController {

    private final ExhibitionService exhibitionService;

    public ExhibitionController(ExhibitionService exhibitionService) {
        this.exhibitionService = exhibitionService;
    }

    @GetMapping
    public PageResponse<ExhibitionDto> list(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate activeOn,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "startDate") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        return exhibitionService.find(title, activeOn, page, size, sortBy, direction);
    }

    @GetMapping("/{id}")
    public ExhibitionDto get(@PathVariable Long id) {
        return exhibitionService.get(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ExhibitionDto create(@Valid @RequestBody CreateExhibitionRequest request) {
        return exhibitionService.create(request);
    }

    @PostMapping("/{exhibitionId}/artworks/{artworkId}")
    public ExhibitionDto addArtwork(@PathVariable Long exhibitionId, @PathVariable Long artworkId) {
        return exhibitionService.addArtwork(exhibitionId, artworkId);
    }
}