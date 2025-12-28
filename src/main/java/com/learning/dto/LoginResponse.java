package com.learning.dto;

public record LoginResponse(
        String accessToken,
        String refreshToken) { }
