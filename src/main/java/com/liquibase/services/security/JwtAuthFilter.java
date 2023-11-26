package com.liquibase.services.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private static final Logger logger = LogManager.getLogger(JwtAuthFilter.class);


    /**
     * https://github.com/hantsy/spring-webmvc-jwt-sample/tree/master
     * explanation of JwtTokenProvider:
     * - https://medium.com/spring-boot/spring-boot-3-spring-security-6-jwt-authentication-authorization-98702d6313a5
     * - https://medium.com/spring-boot/jwt-refresh-token-spring-security-c5b4646cdbd9
     * - https://codersee.com/spring-boot-3-spring-security-6-with-kotlin-jwt/
     * https://medium.com/code-with-farhan/spring-security-jwt-authentication-authorization-a2c6860be3cf
     * https://github.com/Ozair0/Spring-Boot-3-Auth-JWT-Cookie-JPA/blob/master/src/main/java/com/example/security/full/security/UserSecurity/model/UserSecurity.java
     */

    private final JwtTokenProvider jwtTokenProvider;


    @Autowired
    public JwtAuthFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String accessToken = jwtTokenProvider.resolveToken(request);
            if (accessToken != null) {
                Authentication auth = jwtTokenProvider.getAuthentication(accessToken);
                SecurityContext context = SecurityContextHolder.getContext();
                context.setAuthentication(auth);
                SecurityContextHolder.setContext(context);
            }
        } catch (Exception e) {
            Map<String, String> errorDetails = new HashMap<>(2);
            errorDetails.put("message", "Authentication Error");
            errorDetails.put("details", e.getMessage());
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            logger.error("errorDetails: {} {}", e.getMessage(), errorDetails, e);
        }
        filterChain.doFilter(request, response);
    }
}
