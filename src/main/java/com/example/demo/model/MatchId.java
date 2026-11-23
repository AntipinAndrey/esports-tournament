package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/* Естественный составной ключ матча. */
@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class MatchId implements Serializable {
    @Column(name = "tournament_name", length = 150)
    private String tournamentName;

    @Column(name = "first_team_name", length = 100)
    private String firstTeamName;

    @Column(name = "second_team_name", length = 100)
    private String secondTeamName;
}
