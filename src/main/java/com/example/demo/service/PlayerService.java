package com.example.demo.service;

import com.example.demo.dto.PlayerRequest;
import com.example.demo.dto.PlayerResponse;

import java.util.List;

public interface PlayerService {
    List<PlayerResponse> findAll(String teamName, String firstName);

    PlayerResponse findByNickname(String nickname);

    PlayerResponse create(PlayerRequest request);

    PlayerResponse update(String nickname, PlayerRequest request);

    void delete(String nickname);
}
