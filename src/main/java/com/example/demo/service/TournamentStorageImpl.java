package com.example.demo.service;

import com.example.demo.model.Match;
import com.example.demo.model.Tournament;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/* Реализация хранилища на ArrayList.*/
@Service
public class TournamentStorageImpl implements TournamentStorage {
    private final List<Tournament> tournaments = new ArrayList<>();

    @Override
    public void addTournament(Tournament tournament) {
        if (tournament == null) {
            throw new IllegalArgumentException("Tournament cannot be null");
        }
        if (findTournament(tournament.getTournamentName()).isPresent()) {
            throw new IllegalStateException("Tournament already exists");
        }
        tournaments.add(tournament);
    }

    @Override
    public List<Tournament> getAllTournaments() {
        return List.copyOf(tournaments);
    }

    @Override
    public Optional<Tournament> findTournament(String tournamentName) {
        return tournaments.stream()
                .filter(tournament -> tournament.getTournamentName()
                        .equals(tournamentName))
                .findFirst();
    }

    @Override
    public List<Tournament> findByStatus(String status) {
        return tournaments.stream()
                .filter(tournament -> tournament.getStatus().equals(status))
                .toList();
    }

    @Override
    public void changeStatus(String tournamentName, String status) {
        Tournament tournament = getExistingTournament(tournamentName);
        tournament.setStatus(status);
    }

    @Override
    public void deleteTournament(String tournamentName) {
        Tournament tournament = getExistingTournament(tournamentName);
        tournaments.remove(tournament);
    }

    @Override
    public void addMatch(String tournamentName, Match match) {
        Tournament tournament = getExistingTournament(tournamentName);
        tournament.addMatch(match);
    }

    private Tournament getExistingTournament(String tournamentName) {
        return findTournament(tournamentName)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Tournament not found"
                ));
    }
}
