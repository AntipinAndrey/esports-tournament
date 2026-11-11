package com.example.demo.controller;

import com.example.demo.dto.PlayerRequest;
import com.example.demo.dto.PlayerResponse;
import com.example.demo.service.PlayerService;
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
@RequestMapping("/api/players")
@RequiredArgsConstructor
public class PlayerController {
    private final PlayerService playerService;

    @GetMapping
    public List<PlayerResponse> findAll(
            @RequestParam(required = false) String teamName,
            @RequestParam(required = false) String firstName
    ) {
        return playerService.findAll(teamName, firstName);
    }

    @GetMapping("/{nickname}")
    public PlayerResponse findByNickname(@PathVariable String nickname) {
        return playerService.findByNickname(nickname);
    }

    @PostMapping
    public ResponseEntity<PlayerResponse> create(
            @Valid @RequestBody PlayerRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(playerService.create(request));
    }

    @PutMapping("/{nickname}")
    public PlayerResponse update(
            @PathVariable String nickname,
            @Valid @RequestBody PlayerRequest request
    ) {
        return playerService.update(nickname, request);
    }

    @DeleteMapping("/{nickname}")
    public ResponseEntity<Void> delete(@PathVariable String nickname) {
        playerService.delete(nickname);
        return ResponseEntity.noContent().build();
    }
}
