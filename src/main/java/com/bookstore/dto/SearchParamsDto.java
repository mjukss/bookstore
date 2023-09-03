package com.bookstore.dto;

import java.time.Instant;
import java.util.Arrays;

public record SearchParamsDto(
        Integer page,
        Integer size,
        Instant updatedAfter,
        String sortBy,
        String order
) {
}
