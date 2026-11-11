package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TournamentRequest(
        @NotBlank String tournamentName,
        @NotBlank String discipline,
        @NotNull LocalDate startDate,
        @NotNull LocalDate endDate,
        @NotNull BigDecimal prizePool,
        @NotBlank String status,
        @NotBlank String organizerName
) {
}
