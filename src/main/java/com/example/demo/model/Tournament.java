package com.example.demo.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Tournament {
    private final String tournamentName;
    private final String discipline;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final BigDecimal prizePool;
    private String status;
    private final Organizer organizer;
    private final List<Match> matches = new ArrayList<>();

    public Tournament(
            String tournamentName,
            String discipline,
            LocalDate startDate,
            LocalDate endDate,
            BigDecimal prizePool,
            String status,
            Organizer organizer
    ) {
        this.tournamentName = tournamentName;
        this.discipline = discipline;
        this.startDate = startDate;
        this.endDate = endDate;
        this.prizePool = prizePool;
        this.status = status;
        this.organizer = organizer;
    }

    public void addMatch(Match match) {
        if (!matches.contains(match)) {
            matches.add(match);
        }
    }

    public void removeMatch(String teamName) {
        matches.removeIf(match -> match.getTeam().getTeamName().equals(teamName));
    }

    public List<Match> getMatches() {
        return Collections.unmodifiableList(matches);
    }

    public String getTournamentName() {
        return tournamentName;
    }

    public String getDiscipline() {
        return discipline;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public BigDecimal getPrizePool() {
        return prizePool;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Organizer getOrganizer() {
        return organizer;
    }

    @Override
    public String toString() {
        return "Tournament{" +
                "tournamentName='" + tournamentName + '\'' +
                ", discipline='" + discipline + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", prizePool=" + prizePool +
                ", status='" + status + '\'' +
                ", organizer='" + organizer.getOrganizerName() + '\'' +
                ", matches=" + matches.size() +
                '}';
    }
}
