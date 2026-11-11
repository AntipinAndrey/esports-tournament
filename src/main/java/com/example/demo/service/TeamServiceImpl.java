package com.example.demo.service;

import com.example.demo.dto.TeamRequest;
import com.example.demo.dto.TeamResponse;
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
            throw new IllegalStateException("Team already exists");
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
            throw new IllegalArgumentException(
                    "Path teamName must match request teamName"
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
                        "Team not found: " + teamName
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
