package com.hildabur.bambikbaby.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JWTService {
    @Value("${jwt.secret}")
    private String jwtSecret;
    @Value("${jwt.lifeTime}")
    private String jwtLifeTime;

}
