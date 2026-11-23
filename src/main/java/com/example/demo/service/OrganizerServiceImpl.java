package com.example.demo.service;

import com.example.demo.dto.OrganizerRequest;
import com.example.demo.dto.OrganizerResponse;
import com.example.demo.exception.DuplicateResourceException;
import com.example.demo.exception.InvalidRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Organizer;
import com.example.demo.repository.OrganizerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrganizerServiceImpl implements OrganizerService {
    private final OrganizerRepository organizerRepository;

    @Override
    public List<OrganizerResponse> findAll(String country) {
        List<Organizer> organizers = country == null || country.isBlank()
                ? organizerRepository.findAll()
                : organizerRepository.findByCountryIgnoreCase(country);
        return organizers.stream().map(this::toResponse).toList();
    }

    @Override
    public OrganizerResponse findByName(String organizerName) {
        return toResponse(getOrganizer(organizerName));
    }

    @Override
    @Transactional
    public OrganizerResponse create(OrganizerRequest request) {
        if (organizerRepository.existsById(request.organizerName())) {
            throw new DuplicateResourceException(
                    "Organizer already exists: " + request.organizerName(),
                    "Use another organizerName or update the existing organizer with PUT."
            );
        }
        Organizer organizer = new Organizer(
                request.organizerName(),
                request.email(),
                request.phoneNumber(),
                request.country()
        );
        return toResponse(organizerRepository.save(organizer));
    }

    @Override
    @Transactional
    public OrganizerResponse update(
            String organizerName,
            OrganizerRequest request
    ) {
        Organizer organizer = getOrganizer(organizerName);
        if (!organizerName.equals(request.organizerName())) {
            throw new InvalidRequestException(
                    "Path organizerName must match request organizerName",
                    "Use the same organizerName in the URL and in the JSON body."
            );
        }
        organizer.setEmail(request.email());
        organizer.setPhoneNumber(request.phoneNumber());
        organizer.setCountry(request.country());
        return toResponse(organizerRepository.save(organizer));
    }

    @Override
    @Transactional
    public void delete(String organizerName) {
        Organizer organizer = getOrganizer(organizerName);
        organizerRepository.delete(organizer);
    }

    private Organizer getOrganizer(String organizerName) {
        return organizerRepository.findById(organizerName)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Organizer not found: " + organizerName,
                        "Check organizerName or first send GET /api/organizers to see available organizers."
                ));
    }

    private OrganizerResponse toResponse(Organizer organizer) {
        return new OrganizerResponse(
                organizer.getOrganizerName(),
                organizer.getEmail(),
                organizer.getPhoneNumber(),
                organizer.getCountry()
        );
    }
}
