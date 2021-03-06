package com.simplon.pixelartback.facade.security;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    /**
     * Returns the Subject associated with the token
     * @param token
     * @return
     */
    public String getUserNameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    /**
     * Returns the information contained in the token
     *
     * @param token
     * @param claimResolver  Name of the Property
     * @return
     * @param <T>
     */
    private <T> T getClaimFromToken(String token, Function<Claims, T> claimResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimResolver.apply(claims);
    }

    /**
     * Returns all the properties of the token, all the payload of the token object as a JSON object
     *
     * @param token
     * @return
     */
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    /**
     * Validate the token by comparing user emails of the token and the current UserDetails
     *
     * @param token
     * @param userDetails
     * @return
     */
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
        // This ensures that the incoming JSON is automatically converted to a Java Map<String, Object>,
        // handy for JWT as the method "setClaims" simply takes that Map and sets all the claims at once.
        // source: https://www.baeldung.com/java-json-web-tokens-jjwt
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder().setClaims(claims).setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY * 1000))
                .signWith(io.jsonwebtoken.SignatureAlgorithm.HS256, secret)
                .compact();
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
       } catch (UnsupportedJwtException ex) {
            LOGGER.error("Unsupported JWT token");
            throw new UnsupportedJwtException("JWT " + authToken + " Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            LOGGER.error("JWT claims string is empty.");
            throw new IllegalArgumentException("JWT " + authToken + " Illegal argument token");
        }
        return true;
    }

    /**
     * Return the jwt authorities claim encapsulated within the token
     */
    public List<GrantedAuthority> getAuthoritiesFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
        return Arrays.stream(claims.get("authorities").toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
