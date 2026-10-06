package com.example.demo.service;

import com.example.demo.model.AdminUser;
import com.example.demo.model.Match;
import com.example.demo.model.Organizer;
import com.example.demo.model.Player;
import com.example.demo.model.Team;
import com.example.demo.model.Tournament;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class ModelTest {
    @Test
    void shouldCreateUserRoles() {
        AdminUser admin = new AdminUser();

        assertThat(admin.getRole().name()).isEqualTo("ADMIN");
        assertThat(new Organizer(
                "Organizer",
                "organizer@test.ru",
                "+7-900-000-00-00",
                "Russia"
        ).getRole().name()).isEqualTo("ORGANIZER");
        assertThat(new Player(
                "player1",
                "Ivan",
                "Ivanov",
                "player@test.ru"
        ).getRole().name()).isEqualTo("PLAYER");
    }

    @Test
    void shouldAddAndRemovePlayerFromTeam() {
        Team team = new Team("Team Spirit", "TS", "Russia", 1500);
        Player player = new Player(
                "player1",
                "Ivan",
                "Ivanov",
                "player@test.ru"
        );

        team.addPlayer(player);
        assertThat(team.getPlayers()).containsExactly(player);

        team.removePlayer("player1");
        assertThat(team.getPlayers()).isEmpty();
    }

    @Test
    void shouldAddMatchToTournament() {
        Organizer organizer = new Organizer(
                "Organizer",
                "organizer@test.ru",
                "+7-900-000-00-00",
                "Russia"
        );
        Tournament tournament = new Tournament(
                "Test Cup",
                "Dota 2",
                LocalDate.now(),
                LocalDate.now().plusDays(2),
                new BigDecimal("50000"),
                "planned",
                organizer
        );
        Team team = new Team("Team Spirit", "TS", "Russia", 1500);
        Match match = new Match(team, "2:1", "Team Spirit");

        tournament.addMatch(match);

        assertThat(tournament.getMatches()).containsExactly(match);
    }
}
