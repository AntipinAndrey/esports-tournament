package com.example.demo.controller;

import com.example.demo.dto.TournamentRequest;
import com.example.demo.dto.TournamentResponse;
import com.example.demo.service.TournamentService;
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
@RequestMapping("/api/tournaments")
@RequiredArgsConstructor
public class TournamentController {
    private final TournamentService tournamentService;

    @GetMapping
    public List<TournamentResponse> findAll(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String discipline
    ) {
        return tournamentService.findAll(status, discipline);
    }

    @GetMapping("/{tournamentName}")
    public TournamentResponse findByName(@PathVariable String tournamentName) {
        return tournamentService.findByName(tournamentName);
    }

    @PostMapping
    public ResponseEntity<TournamentResponse> create(
            @Valid @RequestBody TournamentRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(tournamentService.create(request));
    }

    @PutMapping("/{tournamentName}")
    public TournamentResponse update(
            @PathVariable String tournamentName,
            @Valid @RequestBody TournamentRequest request
    ) {
        return tournamentService.update(tournamentName, request);
    }

    @DeleteMapping("/{tournamentName}")
    public ResponseEntity<Void> delete(@PathVariable String tournamentName) {
        tournamentService.delete(tournamentName);
        return ResponseEntity.noContent().build();
    }
}
