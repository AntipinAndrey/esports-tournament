package com.example.demo.service;

import com.example.demo.dto.MatchRequest;
import com.example.demo.dto.MatchResponse;
import com.example.demo.model.Match;
import com.example.demo.model.MatchId;
import com.example.demo.model.Team;
import com.example.demo.model.Tournament;
import com.example.demo.repository.MatchRepository;
import com.example.demo.repository.TeamRepository;
import com.example.demo.repository.TournamentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MatchServiceImpl implements MatchService {
    private final MatchRepository matchRepository;
    private final TournamentRepository tournamentRepository;
    private final TeamRepository teamRepository;

    @Override
    public List<MatchResponse> findAll(
            String tournamentName,
            String teamName,
            String winnerName
    ) {
        return matchRepository.findAll().stream()
                .filter(match -> tournamentName == null
                        || tournamentName.isBlank()
                        || match.getTournament().getTournamentName()
                        .equals(tournamentName))
                .filter(match -> teamName == null
                        || teamName.isBlank()
                        || match.getFirstTeam().getTeamName().equals(teamName)
                        || match.getSecondTeam().getTeamName().equals(teamName))
                .filter(match -> winnerName == null
                        || winnerName.isBlank()
                        || match.getWinnerName().equalsIgnoreCase(winnerName))
                .map(this::toResponse)
                .toList();
    }

    @Override
    public MatchResponse findById(
            String tournamentName,
            String firstTeamName,
            String secondTeamName
    ) {
        return toResponse(getMatch(
                tournamentName,
                firstTeamName,
                secondTeamName
        ));
    }

    @Override
    @Transactional
    public MatchResponse create(MatchRequest request) {
        MatchId matchId = toId(request);
        if (matchRepository.existsById(matchId)) {
            throw new IllegalStateException("Match already exists");
        }
        Tournament tournament = getTournament(request.tournamentName());
        Team firstTeam = getTeam(request.firstTeamName());
        Team secondTeam = getTeam(request.secondTeamName());
        validateDifferentTeams(firstTeam, secondTeam);
        validateWinnerName(request.winnerName(), firstTeam, secondTeam);
        Match match = new Match(
                tournament,
                firstTeam,
                secondTeam,
                request.score(),
                request.winnerName()
        );
        validateWinnerByScore(match);
        return toResponse(matchRepository.save(match));
    }

    @Override
    @Transactional
    public MatchResponse update(
            String tournamentName,
            String firstTeamName,
            String secondTeamName,
            MatchRequest request
    ) {
        if (!tournamentName.equals(request.tournamentName())
                || !firstTeamName.equals(request.firstTeamName())
                || !secondTeamName.equals(request.secondTeamName())) {
            throw new IllegalArgumentException(
                    "Path match keys must match request match keys"
            );
        }
        Match match = getMatch(tournamentName, firstTeamName, secondTeamName);
        validateWinnerName(
                request.winnerName(),
                match.getFirstTeam(),
                match.getSecondTeam()
        );
        match.setScore(request.score());
        match.setWinnerName(request.winnerName());
        validateWinnerByScore(match);
        return toResponse(matchRepository.save(match));
    }

    @Override
    @Transactional
    public void delete(
            String tournamentName,
            String firstTeamName,
            String secondTeamName
    ) {
        matchRepository.delete(getMatch(
                tournamentName,
                firstTeamName,
                secondTeamName
        ));
    }

    private Match getMatch(
            String tournamentName,
            String firstTeamName,
            String secondTeamName
    ) {
        return matchRepository.findById(
                        new MatchId(
                                tournamentName,
                                firstTeamName,
                                secondTeamName
                        )
                )
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Match not found"
                ));
    }

    private MatchId toId(MatchRequest request) {
        return new MatchId(
                request.tournamentName(),
                request.firstTeamName(),
                request.secondTeamName()
        );
    }

    private Tournament getTournament(String tournamentName) {
        return tournamentRepository.findById(tournamentName)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Tournament not found: " + tournamentName
                ));
    }

    private Team getTeam(String teamName) {
        return teamRepository.findById(teamName)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Team not found: " + teamName
                ));
    }

    private void validateDifferentTeams(Team firstTeam, Team secondTeam) {
        if (firstTeam.getTeamName().equals(secondTeam.getTeamName())) {
            throw new IllegalStateException(
                    "A match must contain two different teams"
            );
        }
    }

    private void validateWinnerName(
            String winnerName,
            Team firstTeam,
            Team secondTeam
    ) {
        boolean isFirstTeam = firstTeam.getTeamName().equals(winnerName);
        boolean isSecondTeam = secondTeam.getTeamName().equals(winnerName);
        if (!isFirstTeam && !isSecondTeam) {
            throw new IllegalArgumentException(
                    "Winner must be one of the match teams"
            );
        }
    }

    private void validateWinnerByScore(Match match) {
        if (!match.getWinner().getTeamName().equals(match.getWinnerName())) {
            throw new IllegalArgumentException(
                    "Winner name must match the team with the highest score"
            );
        }
    }

    private MatchResponse toResponse(Match match) {
        return new MatchResponse(
                match.getTournament().getTournamentName(),
                match.getFirstTeam().getTeamName(),
                match.getSecondTeam().getTeamName(),
                match.getScore(),
                match.getWinner().getTeamName(),
                match.getWinnerPoints(),
                match.getWinnerWithScore()
        );
    }
}
