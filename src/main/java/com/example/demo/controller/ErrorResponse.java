package com.example.demo.controller;

import java.time.Instant;

/** Единый формат ответа REST API при ошибке. */
public record ErrorResponse(
        Instant timestamp,
        int status,
        String error,
        String message,
        String hint,
        String path
) {
}
