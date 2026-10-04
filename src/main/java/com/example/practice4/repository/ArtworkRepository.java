package com.example.practice4.repository;

import com.example.practice4.entity.Artwork;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ArtworkRepository extends JpaRepository<Artwork, Long> {

    @Override
    @EntityGraph(attributePaths = "artist")
    Page<Artwork> findAll(Pageable pageable);

    // derived
    @EntityGraph(attributePaths = "artist")
    Page<Artwork> findByStyleIgnoreCase(String style, Pageable pageable);

    // derived: картины одного художника (вместо обратной коллекции в Artist)
    @EntityGraph(attributePaths = "artist")
    Page<Artwork> findByArtistId(Long artistId, Pageable pageable);

    // @Query
    @Query(value = "select a from Artwork a join fetch a.artist "
                 + "where lower(a.title) like lower(concat('%', :text, '%'))",
           countQuery = "select count(a) from Artwork a "
                      + "where lower(a.title) like lower(concat('%', :text, '%'))")
    Page<Artwork> searchByTitle(@Param("text") String text, Pageable pageable);
}