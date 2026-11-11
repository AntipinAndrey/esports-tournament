package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TeamRequest(
        @NotBlank String teamName,
        @NotBlank String tag,
        @NotBlank String country,
        @NotNull Integer rating
) {
}
