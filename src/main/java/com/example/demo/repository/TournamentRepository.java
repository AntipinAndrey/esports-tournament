package com.example.demo.repository;

import com.example.demo.model.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TournamentRepository extends JpaRepository<Tournament, String> {
    List<Tournament> findByStatusIgnoreCase(String status);

    List<Tournament> findByDisciplineIgnoreCase(String discipline);

    List<Tournament> findByStatusIgnoreCaseAndDisciplineIgnoreCase(
            String status,
            String discipline
    );
}
