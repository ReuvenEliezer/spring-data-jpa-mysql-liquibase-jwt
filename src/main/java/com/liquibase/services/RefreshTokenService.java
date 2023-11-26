package com.liquibase.services;

import com.liquibase.entities.Employee;
import com.liquibase.entities.RefreshToken;
import com.liquibase.repositories.EmployeeDao;
import com.liquibase.services.security.RefreshTokenRepository;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final EmployeeDao employeeDao;

    @Autowired
    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, EmployeeDao employeeDao) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.employeeDao = employeeDao;
    }

    private static final Duration expirationTimeDuration = Duration.ofMinutes(10);

    public RefreshToken createRefreshToken(String username) {
        Employee employee = employeeDao.findByEmail(username).orElseThrow(() -> new RuntimeException("No user found with username: " + username));
        RefreshToken refreshToken = RefreshToken.builder()
                .email(employee.getEmail())
                .token(UUID.randomUUID().toString())
                .expiryDate(LocalDateTime.now().plus(expirationTimeDuration)) // set expiry of refresh token to 10 minutes - you can configure it application.properties file
                .build();
        refreshTokenRepository.save(refreshToken.getToken(), refreshToken);
        return refreshToken;
    }


    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.get(token);
    }

    public RefreshToken verifyExpiration(RefreshToken refreshToken) {
        if (refreshToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            refreshTokenRepository.remove(refreshToken.getToken());
            throw new ExpiredJwtException(null, null, refreshToken.getToken() + " Refresh token is expired. Please make a new login..!");
        }
        return refreshToken;
    }

}
