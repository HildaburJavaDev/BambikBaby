package com.hildabur.bambikbaby.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWTService {
    @Value("${jwt.secret}")
    private String jwtSecret;
    @Value("${jwt.lifeTime}")
    private String jwtLifeTime;

    public String generateToken(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Map<String, Object> claims = new HashMap<>();
        Calendar calendar = Calendar.getInstance();
        Date issueAt = calendar.getTime();
        calendar.add(Calendar.MILLISECOND, Integer.parseInt(jwtLifeTime));
        Date expireAt = calendar.getTime();
        claims.put("userId", userDetails.getId());
        claims.put("roleName", userDetails.getRoleName());
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(issueAt)
                .setExpiration(expireAt)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public Long getUserIdFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .get("userId", Long.class);
    }

    public String getRoleNameFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .get("roleName", String.class);
    }
}
