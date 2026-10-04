package com.example.practice4.service;

import com.example.practice4.dto.*;
import com.example.practice4.entity.Artist;
import com.example.practice4.exception.NotFoundException;
import com.example.practice4.repository.ArtistRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static org.springframework.util.StringUtils.hasText;

@Service
public class ArtistService {

    private static final Set<String> SORTABLE = Set.of("id", "name", "country", "birthYear");

    private final ArtistRepository artistRepository;

    public ArtistService(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    @Transactional(readOnly = true)
    public PageResponse<ArtistDto> find(String country, String text,
                                        int page, int size, String sortBy, String direction) {
        Pageable pageable = PageRequests.of(page, size, sortBy, direction, SORTABLE, "id");

        Page<Artist> result;
        if (hasText(text)) {
            result = artistRepository.searchByName(text.trim(), pageable);
        } else if (hasText(country)) {
            result = artistRepository.findByCountryIgnoreCase(country.trim(), pageable);
        } else {
            result = artistRepository.findAll(pageable);
        }
        return PageResponse.from(result.map(ArtistService::toDto));
    }

    @Transactional(readOnly = true)
    public ArtistDto get(Long id) {
        return artistRepository.findById(id)
                .map(ArtistService::toDto)
                .orElseThrow(() -> new NotFoundException("Artist " + id + " not found"));
    }

    @Transactional
    public ArtistDto create(CreateArtistRequest request) {
        Artist saved = artistRepository.save(
                new Artist(request.name().trim(), request.country(), request.birthYear()));
        return toDto(saved);
    }

    private static ArtistDto toDto(Artist a) {
        return new ArtistDto(a.getId(), a.getName(), a.getCountry(), a.getBirthYear());
    }
}