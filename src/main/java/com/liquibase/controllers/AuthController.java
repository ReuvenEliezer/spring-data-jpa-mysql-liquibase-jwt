package com.liquibase.controllers;

import com.liquibase.entities.Employee;
import com.liquibase.entities.RefreshToken;
import com.liquibase.entities.login.ErrorResponse;
import com.liquibase.entities.login.LoginRequest;
import com.liquibase.entities.login.RefreshTokenRequest;
import com.liquibase.entities.login.TokenResponse;
import com.liquibase.repositories.EmployeeDao;
import com.liquibase.services.RefreshTokenService;
import com.liquibase.services.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    /**
     * flow logic for login & refresh token :
     *  https://stackoverflow.com/questions/63426347/how-can-i-refresh-tokens-in-spring-security
     */

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final EmployeeDao employeeDao;
    private final RefreshTokenService refreshTokenService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider,
                          EmployeeDao employeeDao, RefreshTokenService refreshTokenService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.employeeDao = employeeDao;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(request.email(), request.password());
        Authentication authentication = authenticationManager.authenticate(auth);

        if (authentication.isAuthenticated()) {
            String email = authentication.getName();
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(request.email());
            String token = jwtTokenProvider.createToken(authentication);
            TokenResponse loginRes = new TokenResponse(token, refreshToken.getToken());
            return ResponseEntity.ok(loginRes);
        }
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @PostMapping("/refresh-token")
    public TokenResponse refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        String refreshToken = refreshTokenRequest.refreshToken();
        Assert.notNull(refreshToken, "Refresh token is null");
        return refreshTokenService.findByToken(refreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getEmail)
                .map(email -> {
                    RefreshToken refreshTokenNew = refreshTokenService.createRefreshToken(email);
                    String accessToken = jwtTokenProvider.createToken(email);
                    return new TokenResponse(accessToken, refreshTokenNew.getToken());
                }).orElseThrow(() -> new RuntimeException("Refresh Token is not in DB..!!"));
    }

    @PostMapping("/register")
    public ResponseEntity<Employee> register(@RequestBody Employee user) {
        return ResponseEntity.ok(employeeDao.save(user));
    }

}
