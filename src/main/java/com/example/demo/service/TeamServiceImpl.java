package com.example.demo.service;

import com.example.demo.dto.TeamRequest;
import com.example.demo.dto.TeamResponse;
import com.example.demo.exception.DuplicateResourceException;
import com.example.demo.exception.InvalidRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Team;
import com.example.demo.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TeamServiceImpl implements TeamService {
    private final TeamRepository teamRepository;

    @Override
    public List<TeamResponse> findAll(String country) {
        List<Team> teams = country == null || country.isBlank()
                ? teamRepository.findAll()
                : teamRepository.findByCountryIgnoreCase(country);
        return teams.stream().map(this::toResponse).toList();
    }

    @Override
    public TeamResponse findByName(String teamName) {
        return toResponse(getTeam(teamName));
    }

    @Override
    @Transactional
    public TeamResponse create(TeamRequest request) {
        if (teamRepository.existsById(request.teamName())) {
            throw new DuplicateResourceException(
                    "Team already exists: " + request.teamName(),
                    "Use another teamName or update the existing team with PUT."
            );
        }
        Team team = new Team(
                request.teamName(),
                request.tag(),
                request.country(),
                request.rating()
        );
        return toResponse(teamRepository.save(team));
    }

    @Override
    @Transactional
    public TeamResponse update(String teamName, TeamRequest request) {
        Team team = getTeam(teamName);
        if (!teamName.equals(request.teamName())) {
            throw new InvalidRequestException(
                    "Path teamName must match request teamName",
                    "Use the same teamName in the URL and in the JSON body."
            );
        }
        team.setTag(request.tag());
        team.setCountry(request.country());
        team.setRating(request.rating());
        return toResponse(teamRepository.save(team));
    }

    @Override
    @Transactional
    public void delete(String teamName) {
        teamRepository.delete(getTeam(teamName));
    }

    private Team getTeam(String teamName) {
        return teamRepository.findById(teamName)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Team not found: " + teamName,
                        "Check teamName or first send GET /api/teams to see available teams."
                ));
    }

    private TeamResponse toResponse(Team team) {
        return new TeamResponse(
                team.getTeamName(),
                team.getTag(),
                team.getCountry(),
                team.getRating(),
                team.getPlayers().size()
        );
    }
}
