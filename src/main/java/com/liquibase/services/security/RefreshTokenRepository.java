package com.liquibase.services.security;

import com.liquibase.entities.RefreshToken;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RefreshTokenRepository {

    private static final Map<String, RefreshToken> refreshTokenToUserDataMap = new ConcurrentHashMap<>();

    public void save(String refreshToken, RefreshToken refreshTokenData) {
        refreshTokenToUserDataMap.put(refreshToken, refreshTokenData);
    }

    public void remove(String refreshToken) {
        refreshTokenToUserDataMap.remove(refreshToken);
    }

    public Optional<RefreshToken> get(String refreshToken) {
        return Optional.of(refreshTokenToUserDataMap.get(refreshToken));
    }

}
