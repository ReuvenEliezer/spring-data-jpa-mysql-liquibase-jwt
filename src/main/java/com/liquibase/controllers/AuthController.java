package com.liquibase.controllers;

import com.liquibase.entities.Employee;
import com.liquibase.entities.login.ErrorResponse;
import com.liquibase.entities.login.LoginRequest;
import com.liquibase.entities.login.LoginResponse;
import com.liquibase.repositories.EmployeeDao;
import com.liquibase.services.security.CustomUserDetailsService;
import com.liquibase.services.security.JwtUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final EmployeeDao employeeDao;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtUtils jwtUtil, EmployeeDao employeeDao) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtil;
        this.employeeDao = employeeDao;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(request.email(), request.password());
        Authentication authentication = authenticationManager.authenticate(auth);
        if (authentication.isAuthenticated()) {
            String email = authentication.getName();
            Employee user = new Employee();
            user.setEmail(email);
            String token = jwtUtils.createToken(user);
            LoginResponse loginRes = new LoginResponse(email, token);
            return ResponseEntity.ok(loginRes);
        }
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<Employee> register(@RequestBody Employee user) {
        return ResponseEntity.ok(employeeDao.save(user));
    }

}
