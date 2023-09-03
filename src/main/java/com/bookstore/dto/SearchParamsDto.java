package com.bookstore.dto;

import java.time.Instant;

public record SearchParamsDto(
        Integer page,
        Integer size,
        Instant updatedAfter,
        String sortBy,
        String order
) {
}
