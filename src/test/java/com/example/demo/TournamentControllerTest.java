package com.example.demo;

import com.example.demo.dto.TournamentResponse;
import com.example.demo.repository.MatchRepository;
import com.example.demo.repository.OrganizerRepository;
import com.example.demo.repository.PlayerRepository;
import com.example.demo.repository.TeamRepository;
import com.example.demo.repository.TournamentRepository;
import com.example.demo.service.TournamentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class TournamentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TournamentService tournamentService;

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
    void shouldReturnTournamentsFilteredByStatusWithoutDatabase() throws Exception {
        TournamentResponse response = new TournamentResponse(
                "Winter Cup",
                "Counter-Strike 2",
                LocalDate.of(2026, 11, 1),
                LocalDate.of(2026, 11, 7),
                new BigDecimal("100000"),
                "planned",
                "Organizer",
                0
        );
        when(tournamentService.findAll("planned", null))
                .thenReturn(List.of(response));

        mockMvc.perform(get("/api/tournaments")
                        .param("status", "planned"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].tournamentName")
                        .value("Winter Cup"))
                .andExpect(jsonPath("$[0].status")
                        .value("planned"));
    }
}
