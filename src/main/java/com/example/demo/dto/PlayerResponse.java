package com.example.demo.dto;

public record PlayerResponse(
        String nickname,
        String firstName,
        String lastName,
        String email,
        String teamName
) {
}
