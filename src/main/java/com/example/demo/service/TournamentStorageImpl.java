package com.example.demo.service;

import com.example.demo.exception.DuplicateResourceException;
import com.example.demo.exception.InvalidRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Match;
import com.example.demo.model.Tournament;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Простая реализация хранилища на ArrayList.
 * Для первой лабораторной работы база данных пока не используется.
 */
@Service
public class TournamentStorageImpl implements TournamentStorage {
    private final List<Tournament> tournaments = new ArrayList<>();

    @Override
    public void addTournament(Tournament tournament) {
        if (tournament == null) {
            throw new InvalidRequestException(
                    "Tournament cannot be null",
                    "Pass a fully initialized tournament object."
            );
        }
        if (findTournament(tournament.getTournamentName()).isPresent()) {
            throw new DuplicateResourceException(
                    "Tournament already exists: " + tournament.getTournamentName(),
                    "Use another tournament name."
            );
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
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Tournament not found: " + tournamentName,
                        "Check the tournament name before changing, deleting or adding a match."
                ));
    }
}
