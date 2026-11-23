package com.example.demo.service;

import com.example.demo.dto.OrganizerRequest;
import com.example.demo.dto.OrganizerResponse;

import java.util.List;

public interface OrganizerService {
    List<OrganizerResponse> findAll(String country);

    OrganizerResponse findByName(String organizerName);

    OrganizerResponse create(OrganizerRequest request);

    OrganizerResponse update(String organizerName, OrganizerRequest request);

    void delete(String organizerName);
}
