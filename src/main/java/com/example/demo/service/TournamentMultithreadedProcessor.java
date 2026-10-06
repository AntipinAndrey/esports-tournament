package com.example.demo.service;

import com.example.demo.model.Team;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


public class TournamentMultithreadedProcessor {

    public List<String> processTeams(List<Team> teams) {
        List<String> threadResults = Collections.synchronizedList(
                new ArrayList<>()
        );
        AtomicInteger threadNumber = new AtomicInteger(1);

        ExecutorService executor = Executors.newFixedThreadPool(
                2,
                runnable -> {
                    Thread thread = new Thread(runnable);
                    thread.setName("team-thread-" + threadNumber.getAndIncrement());
                    return thread;
                }
        );

        for (Team team : teams) {
            executor.submit(() -> {
                String message = "Команда " + team.getTeamName()
                        + " обработана в потоке "
                        + Thread.currentThread().getName();
                System.out.println(message);
                threadResults.add(message);
            });
        }

        executor.shutdown();
        try {
            executor.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Processing interrupted", exception);
        }
        return threadResults;
    }
}
