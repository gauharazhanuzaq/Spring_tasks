package com.example.practice4.service;

import com.example.practice4.dto.*;
import com.example.practice4.entity.Artist;
import com.example.practice4.entity.Artwork;
import com.example.practice4.exception.NotFoundException;
import com.example.practice4.repository.ArtistRepository;
import com.example.practice4.repository.ArtworkRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static org.springframework.util.StringUtils.hasText;

@Service
public class ArtworkService {

    private static final Set<String> SORTABLE = Set.of("id", "title", "createdYear", "price", "style");

    private final ArtworkRepository artworkRepository;
    private final ArtistRepository artistRepository;

    public ArtworkService(ArtworkRepository artworkRepository, ArtistRepository artistRepository) {
        this.artworkRepository = artworkRepository;
        this.artistRepository = artistRepository;
    }

    @Transactional(readOnly = true)
    public PageResponse<ArtworkDto> find(String style, String text,
                                         int page, int size, String sortBy, String direction) {
        Pageable pageable = PageRequests.of(page, size, sortBy, direction, SORTABLE, "id");

        Page<Artwork> result;
        if (hasText(text)) {
            result = artworkRepository.searchByTitle(text.trim(), pageable);
        } else if (hasText(style)) {
            result = artworkRepository.findByStyleIgnoreCase(style.trim(), pageable);
        } else {
            result = artworkRepository.findAll(pageable);
        }
        return PageResponse.from(result.map(ArtworkService::toDto));
    }

    @Transactional(readOnly = true)
    public PageResponse<ArtworkDto> findByArtist(Long artistId,
                                                 int page, int size, String sortBy, String direction) {
        if (!artistRepository.existsById(artistId)) {
            throw new NotFoundException("Artist " + artistId + " not found");
        }
        Pageable pageable = PageRequests.of(page, size, sortBy, direction, SORTABLE, "id");
        return PageResponse.from(
                artworkRepository.findByArtistId(artistId, pageable).map(ArtworkService::toDto));
    }

    @Transactional(readOnly = true)
    public ArtworkDto get(Long id) {
        return artworkRepository.findById(id)
                .map(ArtworkService::toDto)
                .orElseThrow(() -> new NotFoundException("Artwork " + id + " not found"));
    }

    @Transactional
    public ArtworkDto create(CreateArtworkRequest request) {
        Artist artist = artistRepository.findById(request.artistId())
                .orElseThrow(() -> new NotFoundException("Artist " + request.artistId() + " not found"));
        Artwork saved = artworkRepository.save(new Artwork(
                request.title().trim(), request.createdYear(), request.price(), request.style(), artist));
        return toDto(saved);
    }

    private static ArtworkDto toDto(Artwork a) {
        return new ArtworkDto(a.getId(), a.getTitle(), a.getCreatedYear(), a.getPrice(),
                a.getStyle(), a.getArtist().getId(), a.getArtist().getName());
    }
}