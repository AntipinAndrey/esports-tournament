package com.example.demo.service;

import com.example.demo.model.Match;
import com.example.demo.model.Tournament;

import java.util.List;
import java.util.Optional;

/* Интерфейс хранилища турниров. */
public interface TournamentStorage {
    void addTournament(Tournament tournament);

    List<Tournament> getAllTournaments();

    Optional<Tournament> findTournament(String tournamentName);

    List<Tournament> findByStatus(String status);

    void changeStatus(String tournamentName, String status);

    void deleteTournament(String tournamentName);

    void addMatch(String tournamentName, Match match);
}
