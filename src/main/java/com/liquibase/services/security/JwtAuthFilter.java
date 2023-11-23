package com.liquibase.services.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liquibase.entities.Employee;
import com.liquibase.repositories.EmployeeDao;
import com.liquibase.services.ServiceApp;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
//import io.jsonwebtoken.JwtParserBuilder;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Duration;
import java.util.*;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private static final Logger logger = LogManager.getLogger(JwtAuthFilter.class);


    /**
     * https://medium.com/code-with-farhan/spring-security-jwt-authentication-authorization-a2c6860be3cf
     * https://github.com/Ozair0/Spring-Boot-3-Auth-JWT-Cookie-JPA/blob/master/src/main/java/com/example/security/full/security/UserSecurity/model/UserSecurity.java
     */

    private final JwtUtils jwtUtil;

    private final EmployeeDao employeeDao;


    @Autowired
    public JwtAuthFilter(JwtUtils jwtUtil, EmployeeDao employeeDao) {
        this.jwtUtil = jwtUtil;
        this.employeeDao = employeeDao;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String accessToken = jwtUtil.resolveToken(request);
            if (accessToken == null) {
                filterChain.doFilter(request, response);
                return;
            }
            logger.info("token: {}", accessToken);

            //option 1
//            String userEmail = jwtUtil.extractUsername(accessToken);
//            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//                Optional<Employee> employee = employeeDao.findByEmail(userEmail);
//
//                if (employee.isPresent() && jwtUtil.validateToken(accessToken, employee.get().getEmail())) {
//                    UsernamePasswordAuthenticationToken authToken =
//                            new UsernamePasswordAuthenticationToken(userEmail, null, new ArrayList<>());
//                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                    SecurityContextHolder.getContext().setAuthentication(authToken);
//                }
//            }

            //option 2
            jwtUtil.verifyToken(accessToken);
            Authentication authentication = new UsernamePasswordAuthenticationToken(accessToken, "", new ArrayList<>());
            SecurityContextHolder.getContext().setAuthentication(authentication);

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
