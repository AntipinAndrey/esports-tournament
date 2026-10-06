package com.example.demo.service;

import com.example.demo.model.Organizer;
import com.example.demo.model.Tournament;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TournamentStorageTest {
    private TournamentStorage storage;

    @BeforeEach
    void setUp() {
        storage = new TournamentStorageImpl();
    }

    @Test
    void shouldAddFindAndFilterTournaments() {
        Tournament tournament = createTournament("Winter Cup", "planned");
        storage.addTournament(tournament);

        assertThat(storage.getAllTournaments()).containsExactly(tournament);
        assertThat(storage.findTournament("Winter Cup")).contains(tournament);
        assertThat(storage.findByStatus("planned")).containsExactly(tournament);
    }

    @Test
    void shouldChangeStatusAndDeleteTournament() {
        storage.addTournament(createTournament("Summer Cup", "planned"));

        storage.changeStatus("Summer Cup", "finished");
        assertThat(storage.findTournament("Summer Cup").orElseThrow()
                .getStatus()).isEqualTo("finished");

        storage.deleteTournament("Summer Cup");
        assertThat(storage.findTournament("Summer Cup")).isEmpty();
    }

    @Test
    void shouldRejectDuplicateTournament() {
        storage.addTournament(createTournament("Same Cup", "planned"));

        assertThatThrownBy(() -> storage.addTournament(
                createTournament("Same Cup", "planned")
        )).isInstanceOf(IllegalStateException.class);
    }

    private Tournament createTournament(String name, String status) {
        Organizer organizer = new Organizer(
                "Organizer",
                "organizer@test.ru",
                "+7-900-000-00-00",
                "Russia"
        );
        return new Tournament(
                name,
                "Counter-Strike 2",
                LocalDate.of(2026, 11, 1),
                LocalDate.of(2026, 11, 7),
                new BigDecimal("100000"),
                status,
                organizer
        );
    }
}
