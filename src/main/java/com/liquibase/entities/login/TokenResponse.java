package com.liquibase.entities.login;

public record TokenResponse(String token, String refreshToken) {
}
