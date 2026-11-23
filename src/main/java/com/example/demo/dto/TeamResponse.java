package com.example.demo.dto;

public record TeamResponse(
        String teamName,
        String tag,
        String country,
        Integer rating,
        int playersCount
) {
}
