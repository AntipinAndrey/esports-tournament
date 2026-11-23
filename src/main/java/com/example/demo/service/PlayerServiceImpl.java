package com.example.demo.service;

import com.example.demo.dto.PlayerRequest;
import com.example.demo.dto.PlayerResponse;
import com.example.demo.exception.DuplicateResourceException;
import com.example.demo.exception.InvalidRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Player;
import com.example.demo.model.Team;
import com.example.demo.repository.PlayerRepository;
import com.example.demo.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlayerServiceImpl implements PlayerService {
    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;

    @Override
    public List<PlayerResponse> findAll(String teamName, String firstName) {
        List<Player> players;
        if (teamName != null && !teamName.isBlank()) {
            players = playerRepository.findByTeam_TeamName(teamName);
        } else if (firstName != null && !firstName.isBlank()) {
            players = playerRepository.findByFirstNameContainingIgnoreCase(firstName);
        } else {
            players = playerRepository.findAll();
        }
        return players.stream().map(this::toResponse).toList();
    }

    @Override
    public PlayerResponse findByNickname(String nickname) {
        return toResponse(getPlayer(nickname));
    }

    @Override
    @Transactional
    public PlayerResponse create(PlayerRequest request) {
        if (playerRepository.existsById(request.nickname())) {
            throw new DuplicateResourceException(
                    "Player already exists: " + request.nickname(),
                    "Use another nickname or update the existing player with PUT."
            );
        }
        Player player = new Player(
                request.nickname(),
                request.firstName(),
                request.lastName(),
                request.email()
        );
        assignTeam(player, request.teamName());
        return toResponse(playerRepository.save(player));
    }

    @Override
    @Transactional
    public PlayerResponse update(String nickname, PlayerRequest request) {
        Player player = getPlayer(nickname);
        if (!nickname.equals(request.nickname())) {
            throw new InvalidRequestException(
                    "Path nickname must match request nickname",
                    "Use the same nickname in the URL and in the JSON body."
            );
        }
        player.setFirstName(request.firstName());
        player.setLastName(request.lastName());
        player.setEmail(request.email());
        assignTeam(player, request.teamName());
        return toResponse(playerRepository.save(player));
    }

    @Override
    @Transactional
    public void delete(String nickname) {
        playerRepository.delete(getPlayer(nickname));
    }

    private void assignTeam(Player player, String teamName) {
        if (teamName == null || teamName.isBlank()) {
            player.setTeam(null);
            return;
        }
        Team team = teamRepository.findById(teamName)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Team not found: " + teamName,
                        "Check teamName or first send GET /api/teams to see available teams."
                ));
        player.setTeam(team);
    }

    private Player getPlayer(String nickname) {
        return playerRepository.findById(nickname)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Player not found: " + nickname,
                        "Check nickname or first send GET /api/players to see available players."
                ));
    }

    private PlayerResponse toResponse(Player player) {
        String teamName = player.getTeam() == null
                ? null
                : player.getTeam().getTeamName();
        return new PlayerResponse(
                player.getNickname(),
                player.getFirstName(),
                player.getLastName(),
                player.getEmail(),
                teamName
        );
    }
}
