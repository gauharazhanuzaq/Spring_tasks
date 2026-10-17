package com.example.practice4.repository;
import com.example.practice4.entity.Exhibition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;

public interface ExhibitionRepository extends JpaRepository<Exhibition, Long> {
    Page<Exhibition> findByTitleContainingIgnoreCase(String title, Pageable pageable); 

    @Query("select e from Exhibition e where e.startDate <= :date and e.endDate >= :date")
    Page<Exhibition> findActiveOn(@Param("date") LocalDate date, Pageable pageable); 
}
