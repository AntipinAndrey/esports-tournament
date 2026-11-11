package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/* Матч между двумя командами. Составной естественный ключ состоит из турнира и двух команд.*/
@Entity
@Table(name = "matches")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Match {
    @EmbeddedId
    @EqualsAndHashCode.Include
    private MatchId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("tournamentName")
    @JoinColumn(name = "tournament_name", nullable = false)
    private Tournament tournament;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("firstTeamName")
    @JoinColumn(name = "first_team_name", nullable = false)
    private Team firstTeam;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("secondTeamName")
    @JoinColumn(name = "second_team_name", nullable = false)
    private Team secondTeam;

    @Column(nullable = false, length = 20)
    @Setter
    private String score;

    @Column(nullable = false, length = 100)
    @Setter
    private String winnerName;

    public Match(
            Team firstTeam,
            Team secondTeam,
            String score,
            String winnerName
    ) {
        this(null, firstTeam, secondTeam, score, winnerName);
    }

    public Match(
            Tournament tournament,
            Team firstTeam,
            Team secondTeam,
            String score,
            String winnerName
    ) {
        this.tournament = tournament;
        this.firstTeam = firstTeam;
        this.secondTeam = secondTeam;
        this.score = score;
        this.winnerName = winnerName;
        synchronizeId();
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
        synchronizeId();
    }

    public void setFirstTeam(Team firstTeam) {
        this.firstTeam = firstTeam;
        synchronizeId();
    }

    public void setSecondTeam(Team secondTeam) {
        this.secondTeam = secondTeam;
        synchronizeId();
    }

    @PrePersist
    @PreUpdate
    private void synchronizeId() {
        if (tournament != null && firstTeam != null && secondTeam != null) {
            this.id = new MatchId(
                    tournament.getTournamentName(),
                    firstTeam.getTeamName(),
                    secondTeam.getTeamName()
            );
        }
    }

    /* Находит команду с максимальным количеством очков в Map. */
    public Team getWinner() {
        Map<String, Integer> pointsByTeam = calculatePoints();
        String winnerTeamName = pointsByTeam.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElseThrow(() -> new IllegalStateException(
                        "Match must contain two teams"
                ));

        if (firstTeam.getTeamName().equals(winnerTeamName)) {
            return firstTeam;
        }
        if (secondTeam.getTeamName().equals(winnerTeamName)) {
            return secondTeam;
        }
        throw new IllegalStateException("Winner team was not found");
    }

    /* Считает очки команд из строки score формата "2:1". */
    public Map<String, Integer> calculatePoints() {
        if (score == null) {
            throw new IllegalStateException("Score cannot be null");
        }
        String[] scoreParts = score.split(":");
        if (scoreParts.length != 2) {
            throw new IllegalStateException(
                    "Score must contain two numbers separated by ':'"
            );
        }

        Map<String, Integer> pointsByTeam = new LinkedHashMap<>();
        pointsByTeam.put(
                firstTeam.getTeamName(),
                Integer.parseInt(scoreParts[0].trim())
        );
        pointsByTeam.put(
                secondTeam.getTeamName(),
                Integer.parseInt(scoreParts[1].trim())
        );
        return Collections.unmodifiableMap(pointsByTeam);
    }

    /* Возвращает количество очков победившей команды. */
    public int getWinnerPoints() {
        Integer winnerPoints = calculatePoints().get(getWinner().getTeamName());
        if (winnerPoints == null) {
            throw new IllegalStateException("Winner points were not found");
        }
        return winnerPoints;
    }

    /* Возвращает победителя и полный счёт, с которым он выиграл. */
    public String getWinnerWithScore() {
        return getWinner().getTeamName() + " — счёт " + score;
    }

    /* Выводит победителя и счёт матча в консоль. */
    public void printWinner() {
        System.out.println(getWinnerWithScore());
    }

    @Override
    public String toString() {
        return "Match{" +
                "firstTeam='" + firstTeam.getTeamName() + '\'' +
                ", secondTeam='" + secondTeam.getTeamName() + '\'' +
                ", score='" + score + '\'' +
                ", winnerName='" + winnerName + '\'' +
                '}';
    }
}
