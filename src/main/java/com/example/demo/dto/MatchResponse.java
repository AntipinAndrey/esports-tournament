package com.example.demo.dto;

public record MatchResponse(
        String tournamentName,
        String firstTeamName,
        String secondTeamName,
        String score,
        String winnerName,
        int winnerPoints,
        String winnerWithScore
) {
}
