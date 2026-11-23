package com.example.demo.controller;

import com.example.demo.dto.MatchRequest;
import com.example.demo.dto.MatchResponse;
import com.example.demo.service.MatchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/matches")
@RequiredArgsConstructor
public class MatchController {
    private final MatchService matchService;

    @GetMapping
    public List<MatchResponse> findAll(
            @RequestParam(required = false) String tournamentName,
            @RequestParam(required = false) String teamName,
            @RequestParam(required = false) String winnerName
    ) {
        return matchService.findAll(tournamentName, teamName, winnerName);
    }

    @GetMapping("/{tournamentName}/{firstTeamName}/{secondTeamName}")
    public MatchResponse findById(
            @PathVariable String tournamentName,
            @PathVariable String firstTeamName,
            @PathVariable String secondTeamName
    ) {
        return matchService.findById(
                tournamentName,
                firstTeamName,
                secondTeamName
        );
    }

    @PostMapping
    public ResponseEntity<MatchResponse> create(
            @Valid @RequestBody MatchRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(matchService.create(request));
    }

    @PutMapping("/{tournamentName}/{firstTeamName}/{secondTeamName}")
    public MatchResponse update(
            @PathVariable String tournamentName,
            @PathVariable String firstTeamName,
            @PathVariable String secondTeamName,
            @Valid @RequestBody MatchRequest request
    ) {
        return matchService.update(
                tournamentName,
                firstTeamName,
                secondTeamName,
                request
        );
    }

    @DeleteMapping("/{tournamentName}/{firstTeamName}/{secondTeamName}")
    public ResponseEntity<Void> delete(
            @PathVariable String tournamentName,
            @PathVariable String firstTeamName,
            @PathVariable String secondTeamName
    ) {
        matchService.delete(tournamentName, firstTeamName, secondTeamName);
        return ResponseEntity.noContent().build();
    }
}
