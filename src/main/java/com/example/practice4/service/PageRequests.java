package com.example.practice4.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Set;

final class PageRequests {

    static final int MAX_SIZE = 50;

    private PageRequests() { }

    static Pageable of(int page, int size, String sortBy, String direction,
                       Set<String> allowed, String defaultSort) {
        int safePage = Math.max(page, 0);
        int safeSize = Math.min(Math.max(size, 1), MAX_SIZE);
        String property = allowed.contains(sortBy) ? sortBy : defaultSort;
        Sort.Direction dir = "desc".equalsIgnoreCase(direction)
                ? Sort.Direction.DESC : Sort.Direction.ASC;

        Sort sort = Sort.by(dir, property);
        if (!"id".equals(property)) {
            sort = sort.and(Sort.by(Sort.Direction.ASC, "id"));   // стабильный порядок между страницами
        }
        return PageRequest.of(safePage, safeSize, sort);
    }
}