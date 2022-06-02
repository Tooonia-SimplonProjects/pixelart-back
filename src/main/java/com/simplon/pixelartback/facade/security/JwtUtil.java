package com.simplon.pixelartback.facade.security;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 *  Our custom class providing utility methods for managing tokens, like generating, validating, and more.
 */
@Component
public class JwtUtil {
//    TODO: megnezni, ez pontosan mennyi ido!!!
//    1000*30 = 30 seconds /in milliseconds, https://www.baeldung.com/java-json-web-tokens-jjwt
    private static final int TOKEN_VALIDITY = 120000;
//    private static final int TOKEN_VALIDITY = 3600 * 5;

    @Value("${jwt.secret}")
    private String secret;

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtil.class);

    public String getUserNameFromToken(String token) { // TODO: lehet, h ez nekem az email!
        return getClaimFromToken(token, Claims::getSubject);
    }

    //It takes function as argument and return the function
    private <T> T getClaimFromToken(String token, Function<Claims, T> claimResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    public boolean validateToken(String token, AuthenticatedUser authenticatedUser) {
        String userEmail = getUserNameFromToken(token);
        return (userEmail.equals(authenticatedUser.getEmail()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        final Date expirationDate = getExpirationDateFromToken(token);
        return expirationDate.before(new Date());
    }

    private Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

public String generateToken(AuthenticatedUser authenticatedUser) {
//        This ensures that the incoming JSON is automatically converted to a Java Map<String, Object>,
//        handy for JWT as the method "setClaims" simply takes that Map and sets all the claims at once.
//        source: https://www.baeldung.com/java-json-web-tokens-jjwt
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder().setClaims(claims).setSubject(authenticatedUser.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY * 1000))
                .signWith(io.jsonwebtoken.SignatureAlgorithm.HS256, secret)
                .compact();
//        TODO: ide a secret helyett kell a secretService.getHS256SecretBytes()!!!
    }

    /**
     * Returns the user id encapsulated within the token
     */
    public Long getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getSubject());
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken);

        } catch (SignatureException ex) {
            LOGGER.error("Invalid JWT signature");
            throw new SignatureException("JWT " + authToken + " Incorrect signature", ex);

        } catch (MalformedJwtException ex) {
            LOGGER.error("Invalid JWT token");
            throw new MalformedJwtException("JWT " + authToken + " Malformed jwt token", ex);

//        } catch (ExpiredJwtException ex) {
//            LOGGER.error("Expired JWT token");
//            throw new ExpiredJwtException(H"JWT " + authToken + " Token expired. Refresh required", ex);

        } catch (UnsupportedJwtException ex) {
            LOGGER.error("Unsupported JWT token");
            throw new UnsupportedJwtException("JWT " + authToken + " Unsupported JWT token");

        } catch (IllegalArgumentException ex) {
            LOGGER.error("JWT claims string is empty.");
            throw new IllegalArgumentException("JWT " + authToken + " Illegal argument token");
        }

        return true;
    }
}
