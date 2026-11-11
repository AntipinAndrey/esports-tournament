package com.example.demo.model;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;


public class Match {
    private final Team firstTeam;
    private final Team secondTeam;
    private final String score;
    private final String winnerName;

    public Match(
            Team firstTeam,
            Team secondTeam,
            String score,
            String winnerName
    ) {
        this.firstTeam = firstTeam;
        this.secondTeam = secondTeam;
        this.score = score;
        this.winnerName = winnerName;
    }

    public Team getFirstTeam() {
        return firstTeam;
    }

    public Team getSecondTeam() {
        return secondTeam;
    }

    public String getScore() {
        return score;
    }

    public String getWinnerName() {
        return winnerName;
    }

    /* Находит победившую команду по максимальному числу очков в Map.*/
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

    
    public Map<String, Integer> calculatePoints() {
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

    /* Возвращает количество очков победившей команды.*/
    public int getWinnerPoints() {
        Integer winnerPoints = calculatePoints().get(getWinner().getTeamName());
        if (winnerPoints == null) {
            throw new IllegalStateException("Winner points were not found");
        }
        return winnerPoints;
    }

    /*Возвращает победителя и полный счёт, с которым он выиграл.*/
    public String getWinnerWithScore() {
        return getWinner().getTeamName() + " — счёт " + score;
    }

    
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
