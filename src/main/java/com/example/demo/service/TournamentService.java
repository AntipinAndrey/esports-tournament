package com.example.demo.service;

import com.example.demo.dto.TournamentRequest;
import com.example.demo.dto.TournamentResponse;

import java.util.List;

public interface TournamentService {
    List<TournamentResponse> findAll(String status, String discipline);

    TournamentResponse findByName(String tournamentName);

    TournamentResponse create(TournamentRequest request);

    TournamentResponse update(String tournamentName, TournamentRequest request);

    void delete(String tournamentName);
}
