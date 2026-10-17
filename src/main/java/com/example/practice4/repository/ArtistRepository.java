package com.example.practice4.repository;
import com.example.practice4.entity.Artist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ArtistRepository extends JpaRepository<Artist, Long> {
    Page<Artist> findByCountryIgnoreCase(String country, Pageable pageable);        

    @Query("select a from Artist a where lower(a.name) like lower(concat('%', :text, '%'))")
    Page<Artist> searchByName(@Param("text") String text, Pageable pageable);        
}