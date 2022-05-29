package com.simplon.pixelartback.facade.security;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// Our custom filter class that allows to validate the JWT Token.
//It has to extend "OncePerRequestFilter" class that is provided by Spring Security.

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    @Lazy
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        Getting the token that certifies whether the user is authenticated or not:
        final String header = request.getHeader("Authorization");
        String jwtToken = null;
        String userEmail = null;

        if (header != null && header.startsWith("Bearer ")) {
//            "beginIndex": 7 will cut off the "Bearer " part at the head of the JWT Token and return the token:
            jwtToken = header.substring(7);
            try {
                //TODO: valszeg itt email lesz nekem!
                userEmail = jwtUtil.getUserNameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get JWT token");
            } catch (ExpiredJwtException e) {
                System.out.println("Jwt token is expired");
            }
        } else if (header == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // Status code (401) indicating that the request requires HTTP authentication.
        } else {
            System.out.println("Jwt token does not start with Bearer");
        }

        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = jwtService.loadUserByUsername(userEmail); //TODO: valszeg ez a UserDetailsServiceImpl class-om lesz?!, "email"-lel
//  We are validating the token with the userDetails, in order to know that the token is valid for the given user.
//  that will verify that the user email matches and the token has not expired:
            if (jwtUtil.validateToken(jwtToken, userDetails)) {
//                TODO: mier "null" a password?
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
//        TODO: nem ertem pontosan (source: JwtRequestFilter):
//         "Si un token d'authent a expiré ou qu'on a refresh le token on doit bloquer la chaîne de filtres"
//        At that point, we have to continue the filter chain:
        filterChain.doFilter(request, response);
    }
}
