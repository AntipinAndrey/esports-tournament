package com.example.demo.service;

import com.example.demo.dto.TeamRequest;
import com.example.demo.dto.TeamResponse;

import java.util.List;

public interface TeamService {
    List<TeamResponse> findAll(String country);

    TeamResponse findByName(String teamName);

    TeamResponse create(TeamRequest request);

    TeamResponse update(String teamName, TeamRequest request);

    void delete(String teamName);
}
