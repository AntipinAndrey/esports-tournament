package com.example.demo.repository;

import com.example.demo.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, String> {
    List<Player> findByTeam_TeamName(String teamName);

    List<Player> findByFirstNameContainingIgnoreCase(String firstName);
}
