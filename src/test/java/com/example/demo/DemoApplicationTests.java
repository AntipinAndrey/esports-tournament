package com.example.demo;

import com.example.demo.repository.MatchRepository;
import com.example.demo.repository.OrganizerRepository;
import com.example.demo.repository.PlayerRepository;
import com.example.demo.repository.TeamRepository;
import com.example.demo.repository.TournamentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
@ActiveProfiles("test")
class DemoApplicationTests {
    @MockitoBean
    private OrganizerRepository organizerRepository;

    @MockitoBean
    private TeamRepository teamRepository;

    @MockitoBean
    private PlayerRepository playerRepository;

    @MockitoBean
    private TournamentRepository tournamentRepository;

    @MockitoBean
    private MatchRepository matchRepository;

    @Test
    void contextLoadsWithoutDatabaseConnection() {
    }
}
