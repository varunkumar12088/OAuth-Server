package com.learning.dto;

public record UserDto (
        String username,
        String password,
        String role
) {
}
