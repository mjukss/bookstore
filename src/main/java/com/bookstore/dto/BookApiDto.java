package com.bookstore.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static java.time.ZoneOffset.UTC;
import static java.time.format.DateTimeFormatter.ofPattern;

public record BookApiDto(
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
        @NotNull
        Instant createdAt,
        @NotNull
        Instant updatedAt,
        @NotNull
        @DecimalMin(value = "0.01")
        @DecimalMax(value = "9999999.99")
        BigDecimal price
) {
    public BookViewDto toBookView() {
        var format = ofPattern("H:mm d MMM yyyy", Locale.US);

        return new BookViewDto(
                id,
                title,
                author,
                releaseYear,
                createdAt().atZone(UTC).format(format),
                updatedAt().atZone(UTC).format(format),
                price
        );
    }
}
