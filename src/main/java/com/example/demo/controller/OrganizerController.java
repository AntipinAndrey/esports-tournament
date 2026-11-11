package com.example.demo.controller;

import com.example.demo.dto.OrganizerRequest;
import com.example.demo.dto.OrganizerResponse;
import com.example.demo.service.OrganizerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/organizers")
@RequiredArgsConstructor
public class OrganizerController {
    private final OrganizerService organizerService;

    @GetMapping
    public List<OrganizerResponse> findAll(
            @RequestParam(required = false) String country
    ) {
        return organizerService.findAll(country);
    }

    @GetMapping("/{organizerName}")
    public OrganizerResponse findByName(@PathVariable String organizerName) {
        return organizerService.findByName(organizerName);
    }

    @PostMapping
    public ResponseEntity<OrganizerResponse> create(
            @Valid @RequestBody OrganizerRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(organizerService.create(request));
    }

    @PutMapping("/{organizerName}")
    public OrganizerResponse update(
            @PathVariable String organizerName,
            @Valid @RequestBody OrganizerRequest request
    ) {
        return organizerService.update(organizerName, request);
    }

    @DeleteMapping("/{organizerName}")
    public ResponseEntity<Void> delete(@PathVariable String organizerName) {
        organizerService.delete(organizerName);
        return ResponseEntity.noContent().build();
    }
}
