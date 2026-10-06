package com.example.demo.model;


public class Match {
    private final Team team;
    private final String score;
    private final String winnerName;

    public Match(Team team, String score, String winnerName) {
        this.team = team;
        this.score = score;
        this.winnerName = winnerName;
    }

    public Team getTeam() {
        return team;
    }

    public String getScore() {
        return score;
    }

    public String getWinnerName() {
        return winnerName;
    }

    @Override
    public String toString() {
        return "Match{" +
                "team='" + team.getTeamName() + '\'' +
                ", score='" + score + '\'' +
                ", winnerName='" + winnerName + '\'' +
                '}';
    }
}
