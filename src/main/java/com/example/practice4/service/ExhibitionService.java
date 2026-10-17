package com.example.practice4.service;

import com.example.practice4.dto.*;
import com.example.practice4.entity.Artwork;
import com.example.practice4.entity.Exhibition;
import com.example.practice4.exception.NotFoundException;
import com.example.practice4.repository.ArtworkRepository;
import com.example.practice4.repository.ExhibitionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Set;

import static org.springframework.util.StringUtils.hasText;

@Service
public class ExhibitionService {

    private static final Set<String> SORTABLE = Set.of("id", "title", "startDate", "endDate");

    private final ExhibitionRepository exhibitionRepository;
    private final ArtworkRepository artworkRepository;

    public ExhibitionService(ExhibitionRepository exhibitionRepository,
                             ArtworkRepository artworkRepository) {
        this.exhibitionRepository = exhibitionRepository;
        this.artworkRepository = artworkRepository;
    }

    @Transactional(readOnly = true)
    public PageResponse<ExhibitionDto> find(String title, LocalDate activeOn,
                                            int page, int size, String sortBy, String direction) {
        Pageable pageable = PageRequests.of(page, size, sortBy, direction, SORTABLE, "startDate");

        Page<Exhibition> result;
        if (activeOn != null) {
            result = exhibitionRepository.findActiveOn(activeOn, pageable);
        } else if (hasText(title)) {
            result = exhibitionRepository.findByTitleContainingIgnoreCase(title.trim(), pageable);
        } else {
            result = exhibitionRepository.findAll(pageable);
        }
        return PageResponse.from(result.map(ExhibitionService::toDto));
    }

    @Transactional(readOnly = true)
    public ExhibitionDto get(Long id) {
        return exhibitionRepository.findById(id)
                .map(ExhibitionService::toDto)
                .orElseThrow(() -> new NotFoundException("Exhibition " + id + " not found"));
    }

    @Transactional
    public ExhibitionDto create(CreateExhibitionRequest request) {
        if (request.endDate().isBefore(request.startDate())) {
            throw new IllegalArgumentException("endDate must not be before startDate");
        }
        Exhibition saved = exhibitionRepository.save(
                new Exhibition(request.title().trim(), request.startDate(), request.endDate()));
        return toDto(saved);
    }

    @Transactional
    public ExhibitionDto addArtwork(Long exhibitionId, Long artworkId) {
        Exhibition exhibition = exhibitionRepository.findById(exhibitionId)
                .orElseThrow(() -> new NotFoundException("Exhibition " + exhibitionId + " not found"));
        Artwork artwork = artworkRepository.findById(artworkId)
                .orElseThrow(() -> new NotFoundException("Artwork " + artworkId + " not found"));
        exhibition.addArtwork(artwork);   
        return toDto(exhibition);      
    }

    private static ExhibitionDto toDto(Exhibition e) {
        return new ExhibitionDto(e.getId(), e.getTitle(), e.getStartDate(), e.getEndDate());
    }
}