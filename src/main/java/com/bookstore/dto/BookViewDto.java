package com.bookstore.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.Instant;

public record BookViewDto(
        Long id,
        @NotNull
        @NotBlank
        String title,
        @NotNull
        @NotBlank
        String author,
        @NotNull
        @NotBlank
        String releaseYear,
        String createdAt,
        String updatedAt,
        @DecimalMin(value = "0.01")
        @DecimalMax(value = "9999999.99")
        BigDecimal price
) {
}
