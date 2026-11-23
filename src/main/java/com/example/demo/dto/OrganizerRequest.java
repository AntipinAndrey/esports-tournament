package com.example.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record OrganizerRequest(
        @NotBlank String organizerName,
        @NotBlank @Email String email,
        @NotBlank String phoneNumber,
        @NotBlank String country
) {
}
