package com.bookstore.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record UpdateBookDto(
        @NotNull
        Long id,
        @NotNull
        @DecimalMin(value = "0.01")
        @DecimalMax(value = "9999999.99")
        BigDecimal price
) {
}
