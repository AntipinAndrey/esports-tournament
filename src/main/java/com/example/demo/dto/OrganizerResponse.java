package com.example.demo.dto;

public record OrganizerResponse(
        String organizerName,
        String email,
        String phoneNumber,
        String country
) {
}
