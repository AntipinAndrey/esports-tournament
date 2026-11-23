package com.example.demo.service;

import com.example.demo.model.Team;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TournamentMultithreadedProcessorTest {
    @Test
    void shouldProcessTeamsInNamedThreads() {
        List<Team> teams = List.of(
                new Team("Team One", "ONE", "Russia", 1000),
                new Team("Team Two", "TWO", "Russia", 1100),
                new Team("Team Three", "THR", "Russia", 1200),
                new Team("Team Four", "FOU", "Russia", 1300)
        );

        List<String> result = new TournamentMultithreadedProcessor()
                .processTeams(teams);

        assertThat(result).hasSize(4);
        assertThat(result)
                .allMatch(message -> message.contains("team-thread-"));
        assertThat(result.stream()
                .map(message -> message.substring(message.indexOf("team-thread-")))
                .distinct())
                .hasSizeGreaterThan(1);
    }
}
