package ru.vsouth.springbootpractice.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.vsouth.springbootpractice.entity.UserInfo;

import java.security.Key;

@Service
public class JwtService {
    @Value("${secret}")
    private String secret;

    public String generateToken(String username) {
        return Jwts.builder()
                .setIssuer("vsouth")
                .setSubject(username)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
    }

    private Jws<Claims> parseToken(String token){

        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token);
    }
    public String extractUsername(String token) {
        return parseToken(token).getBody().getSubject();
    }

    public String extractIssuer(String token) {
        return parseToken(token).getBody().getIssuer();
    }

    public boolean isTokenValid(String token) {
        final String userName = extractUsername(token);
        final String issuer = extractIssuer(token);
        return (!userName.isBlank() && issuer.equals("vsouth"));
    }


    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
