package com.example.practice4.dto;
import java.time.LocalDate;

public record ExhibitionDto(Long id, String title, LocalDate startDate, LocalDate endDate) { }
