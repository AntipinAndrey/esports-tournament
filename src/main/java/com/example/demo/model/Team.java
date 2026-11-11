package com.example.demo.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* Команда и её состав. Название используется как естественный ключ. */
@Entity
@Table(name = "teams")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class Team {
    @Id
    @jakarta.persistence.Column(length = 100)
    @EqualsAndHashCode.Include
    @ToString.Include
    private String teamName;

    @jakarta.persistence.Column(nullable = false, length = 30)
    @ToString.Include
    private String tag;

    @jakarta.persistence.Column(nullable = false, length = 100)
    @ToString.Include
    private String country;

    @jakarta.persistence.Column(nullable = false)
    @ToString.Include
    private Integer rating;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Player> players = new ArrayList<>();

    public Team(
            String teamName,
            String tag,
            String country,
            Integer rating
    ) {
        this.teamName = teamName;
        this.tag = tag;
        this.country = country;
        this.rating = rating;
    }

    public void addPlayer(Player player) {
        if (!players.contains(player)) {
            players.add(player);
            player.setTeam(this);
        }
    }

    public void removePlayer(String nickname) {
        players.removeIf(player -> {
            boolean isRemoved = player.getNickname().equals(nickname);
            if (isRemoved) {
                player.setTeam(null);
            }
            return isRemoved;
        });
    }

    public List<Player> getPlayers() {
        return Collections.unmodifiableList(players);
    }
}
