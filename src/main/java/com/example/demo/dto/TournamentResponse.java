package com.example.demo.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TournamentResponse(
        String tournamentName,
        String discipline,
        LocalDate startDate,
        LocalDate endDate,
        BigDecimal prizePool,
        String status,
        String organizerName,
        int matchesCount
) {
}
