package com.example.demo.repository;

import com.example.demo.model.Match;
import com.example.demo.model.MatchId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<Match, MatchId> {
}
