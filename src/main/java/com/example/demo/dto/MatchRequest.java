package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;

public record MatchRequest(
        @NotBlank String tournamentName,
        @NotBlank String firstTeamName,
        @NotBlank String secondTeamName,
        @NotBlank String score,
        @NotBlank String winnerName
) {
}
