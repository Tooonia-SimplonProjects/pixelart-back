package com.simplon.pixelartback.facade.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

//Our custom class providing utility methods for managing tokens, like validating, generating, and more.
@Component
public class JwtUtil {
//    TODO: megnezni, ez pontosan mennyi ido!!!
    private static final int TOKEN_VALIDITY = 3600 * 5;

    @Value("${jwt.secret}")
    private String secret;

    public String getUserNameFromToken(String token) { // TODO: lehet, h ez nekem az email!
        return getClaimFromToken(token, Claims::getSubject);
    }

    //It takes function as argument and return the function
    private <T> T getClaimFromToken(String token, Function<Claims, T> claimResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimResolver.apply(claims);
    }

//    TODO: ide mit kell a setSigningKey-hez beirjak?
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String userEmail = getUserNameFromToken(token);
        return (userEmail.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        final Date expirationDate = getExpirationDateFromToken(token);
        return expirationDate.before(new Date());
    }

    private Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder().setClaims(claims).setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY * 1000))
                .signWith(io.jsonwebtoken.SignatureAlgorithm.HS256, secret) // TODO: mi a secretKey?
                .compact();
    }
}
