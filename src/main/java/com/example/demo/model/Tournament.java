package com.example.demo.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Турнир. Название используется как естественный ключ. */
@Entity
@Table(name = "tournaments")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class Tournament {
    @Id
    @jakarta.persistence.Column(length = 150)
    @EqualsAndHashCode.Include
    @ToString.Include
    private String tournamentName;

    @jakarta.persistence.Column(nullable = false, length = 100)
    @ToString.Include
    private String discipline;

    @jakarta.persistence.Column(nullable = false)
    @ToString.Include
    private LocalDate startDate;

    @jakarta.persistence.Column(nullable = false)
    @ToString.Include
    private LocalDate endDate;

    @jakarta.persistence.Column(nullable = false, precision = 14, scale = 2)
    @ToString.Include
    private BigDecimal prizePool;

    @jakarta.persistence.Column(nullable = false, length = 50)
    @ToString.Include
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizer_name", nullable = false)
    private Organizer organizer;

    @OneToMany(
            mappedBy = "tournament",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Match> matches = new ArrayList<>();

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
            match.setTournament(this);
        }
    }

    public void removeMatch(String teamName) {
        matches.removeIf(match ->
                match.getFirstTeam().getTeamName().equals(teamName)
                        || match.getSecondTeam().getTeamName().equals(teamName));
    }

    public List<Match> getMatches() {
        return Collections.unmodifiableList(matches);
    }
}
