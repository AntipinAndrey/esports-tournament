package com.example.demo.service;

import com.example.demo.dto.MatchRequest;
import com.example.demo.dto.MatchResponse;

import java.util.List;

public interface MatchService {
    List<MatchResponse> findAll(
            String tournamentName,
            String teamName,
            String winnerName
    );

    MatchResponse findById(
            String tournamentName,
            String firstTeamName,
            String secondTeamName
    );

    MatchResponse create(MatchRequest request);

    MatchResponse update(
            String tournamentName,
            String firstTeamName,
            String secondTeamName,
            MatchRequest request
    );

    void delete(
            String tournamentName,
            String firstTeamName,
            String secondTeamName
    );
}
