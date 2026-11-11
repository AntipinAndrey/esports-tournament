package com.example.demo.controller;

import com.example.demo.dto.TeamRequest;
import com.example.demo.dto.TeamResponse;
import com.example.demo.service.TeamService;
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
@RequestMapping("/api/teams")
@RequiredArgsConstructor
public class TeamController {
    private final TeamService teamService;

    @GetMapping
    public List<TeamResponse> findAll(
            @RequestParam(required = false) String country
    ) {
        return teamService.findAll(country);
    }

    @GetMapping("/{teamName}")
    public TeamResponse findByName(@PathVariable String teamName) {
        return teamService.findByName(teamName);
    }

    @PostMapping
    public ResponseEntity<TeamResponse> create(
            @Valid @RequestBody TeamRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(teamService.create(request));
    }

    @PutMapping("/{teamName}")
    public TeamResponse update(
            @PathVariable String teamName,
            @Valid @RequestBody TeamRequest request
    ) {
        return teamService.update(teamName, request);
    }

    @DeleteMapping("/{teamName}")
    public ResponseEntity<Void> delete(@PathVariable String teamName) {
        teamService.delete(teamName);
        return ResponseEntity.noContent().build();
    }
}
