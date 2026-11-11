package com.example.demo.repository;

import com.example.demo.model.Organizer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrganizerRepository extends JpaRepository<Organizer, String> {
    List<Organizer> findByCountryIgnoreCase(String country);
}
