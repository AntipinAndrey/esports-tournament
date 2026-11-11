package com.example.demo.controller;

import java.time.Instant;

public record ApiError(Instant timestamp, String message) {
}
