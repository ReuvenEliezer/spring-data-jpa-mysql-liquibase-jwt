package com.liquibase.services.security;

import com.liquibase.controllers.DateTimeController;
import com.liquibase.entities.Employee;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Component
public class JwtUtils {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    private static final SecretKey secret = Keys.secretKeyFor(SignatureAlgorithm.HS256);
//    private static final Key secret = MacProvider.generateKey(SignatureAlgorithm.HS256);
    private static final byte[] secretBytes = secret.getEncoded();
    private static final String base64SecretBytes = Base64.getEncoder().encodeToString(secretBytes);

    private static final String SECRET = "eyJhbGciOiJIUzUxMiJ9.eyJSb2xlIjoiQWRtaW4iLCJJc3N1ZXIiOiJJc3N1ZXIiLCJVc2VybmFtZSI6IkphdmFJblVzZSIsImV4cCI6MTcwMDY3MjcxOSwiaWF0IjoxNzAwNjcyNzE5fQ.2fwao_subAj8bp_P0E-RE-sDjybeYoaTMy77LI3CLUNE8UyP7kQ40YwJmH4BkQf2yUuo_abnAHQZwNFEDt1ABA";
    //    private static final JwtParser jwtParser = Jwts.parser().key().and().build();
    private static final Duration accessTokenValidityDuration = Duration.ofMinutes(10);
    private static final String TOKEN_PREFIX = "Bearer ";


    public String createToken(Employee employee) {
        String id = UUID.randomUUID().toString().replace("-", "");
        Date tokenCreateTime = new Date();
        Date tokenValidity = Date.from(tokenCreateTime.toInstant().plus(accessTokenValidityDuration));

        return Jwts.builder()
                .id(id)
                .subject(employee.getEmail())
                .issuedAt(tokenCreateTime)
                .notBefore(tokenCreateTime)
                .expiration(tokenValidity)
                .signWith(secret, Jwts.SIG.HS256)
                .compact();

//        Claims claims = Jwts.claims()
//                .setSubject(employee.getEmail());
//                .add("first_name", employee.getFirstName())
//                .add("last_Name", employee.getLastName())
//                .build();

//        return Jwts.builder()
//                .setClaims(claims)
//                .setExpiration(tokenValidity)
//                .signWith(SignatureAlgorithm.HS256, SECRET.getBytes(StandardCharsets.UTF_8))
////                .signWith(getSigningKey())
////                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
//                .compact();
    }

    public void verifyToken(String token) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException {
        Claims claims = parseJwtClaims(token);
        logger.info("----------------------------");
        logger.info("ID: {}", claims.getId());
        logger.info("Subject: {}", claims.getSubject());
        logger.info("Issuer: {}", claims.getIssuer());
        logger.info("Expiration: {}", claims.getExpiration());
    }

//    private Key getSigningKey() {
//        byte[] keyBytes = SECRET.getBytes(StandardCharsets.UTF_8);
//        return Keys.hmacShaKeyFor(keyBytes);
//    }

    private Claims parseJwtClaims(String token) {
        return Jwts.parser()
                .verifyWith(secret)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Claims resolveClaims(HttpServletRequest req) {
        try {
            String token = resolveToken(req);
            if (token != null) {
                return parseJwtClaims(token);
            }
            return null;
        } catch (ExpiredJwtException ex) {
            req.setAttribute("expired", ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            req.setAttribute("invalid", ex.getMessage());
            throw ex;
        }
    }

//    public String resolveToken(HttpServletRequest request) throws AuthenticationException {
//        final String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
//        if (bearerToken == null) {
//            throw new PreAuthenticatedCredentialsNotFoundException("missing " + HttpHeaders.AUTHORIZATION + " " + TOKEN_PREFIX);
//        }
//        if (!bearerToken.startsWith(TOKEN_PREFIX)) {
//            throw new InternalAuthenticationServiceException("auto header not start with " + TOKEN_PREFIX);
//        }
//        return bearerToken.substring(TOKEN_PREFIX.length());
//    }

    public String resolveToken(HttpServletRequest request) {
        final String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(TOKEN_PREFIX.length());
        }
        return null;
    }

    public boolean validateClaims(Claims claims) throws AuthenticationException {
        return claims.getExpiration().after(new Date());
    }

    public String getEmail(Claims claims) {
        return claims.getSubject();
    }

    private List<String> getRoles(Claims claims) {
        return (List<String>) claims.get("roles");
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = parseJwtClaims(token);
        return claimsResolver.apply(claims);
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    //
//    public String generateToken(UserDetails userDetails) {
//        Map<String, Object> claims = new HashMap<>();
//        claims.put("authorities",userDetails.getAuthorities());
//        return createToken(claims, userDetails.getUsername());
//    }
//
//    private String createToken(Map<String, Object> claims, String subject) {
//
//        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
//                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
//    }
//
    public boolean validateToken(String token, String email) {
        final String username = extractUsername(token);
        return username.equals(email) && !isTokenExpired(token);
    }
}
