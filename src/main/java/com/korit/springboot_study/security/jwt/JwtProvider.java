package com.korit.springboot_study.security.jwt;

import com.korit.springboot_study.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtProvider {

    private Key key;

    public JwtProvider(@Value("${jwt.secret}") String secret) {
        key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    private Date getExpireDate() {
        return new Date(new Date().getTime() + (1000l * 60 * 60 * 24 * 365));
    }

    public String createAccessToken(User user) {
        return Jwts.builder()
                .claim("userId", user.getUserId())
                .claim("roles", user.getUserRoles().stream().map(userRole -> userRole.getRole().getRoleName()).collect(Collectors.toList()))
                .setExpiration(getExpireDate())
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

    }

    public boolean validateToken(String token) {
        return token != null && token.startsWith("Bearer ");
    }

    public Claims parseToken(String token) {
        Claims claims = null;
        try {
            JwtParser parser = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build();
            claims = parser.parseClaimsJws(token).getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return claims;
    }

    // Authorization -> AccessToken(Bearer ?????.?????.?????)
    public String removeBearer(String bearerToken) {
        String token = null;
        final String BEARER_KEYWORD = "Bearer ";
        if(bearerToken.startsWith(BEARER_KEYWORD)) {
            token = bearerToken.substring(BEARER_KEYWORD.length());
        }
        return token;
    }

}