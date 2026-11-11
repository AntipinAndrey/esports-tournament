package com.example.demo.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Team {
    private final String teamName;
    private final String tag;
    private final String country;
    private final Integer rating;
    private final List<Player> players = new ArrayList<>();

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
        }
    }

    public void removePlayer(String nickname) {
        players.removeIf(player -> player.getNickname().equals(nickname));
    }

    public List<Player> getPlayers() {
        return Collections.unmodifiableList(players);
    }

    public String getTeamName() {
        return teamName;
    }

    public String getTag() {
        return tag;
    }

    public String getCountry() {
        return country;
    }

    public Integer getRating() {
        return rating;
    }

    @Override
    public String toString() {
        return "Team{" +
                "teamName='" + teamName + '\'' +
                ", tag='" + tag + '\'' +
                ", country='" + country + '\'' +
                ", rating=" + rating +
                ", players=" + players.size() +
                '}';
    }
}
