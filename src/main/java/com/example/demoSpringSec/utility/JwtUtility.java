package com.example.demoSpringSec.utility;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtility {
    private final String jwtSecret = "MyJwtSecretKeyMyJwtSecretKeyMyJwtSecretKey!"; // 256-bit key for HS256
    private final long jwtExpirationsMs=86400000;
    private final Key key= Keys.hmacShaKeyFor(jwtSecret.getBytes());

    public String generateToken(String username){
        return Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS256)
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationsMs))
                .setIssuedAt(new Date())
                .setSubject(username)
                .compact();
    }

    public String getUsernameFromJwt(String token){
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken){
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(authToken);
            return true;
        } catch (JwtException e) {
            System.out.println("Invalid JWT: " + e.getMessage());
        }
        return false;
    }
}
