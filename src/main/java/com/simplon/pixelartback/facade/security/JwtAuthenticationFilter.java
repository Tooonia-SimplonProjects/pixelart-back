package com.simplon.pixelartback.facade.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simplon.pixelartback.storage.dto.UserDto;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Filter class extending OncePerRequestFilter responsible for retrieving information about the eventual
 * connected User from the Header of the HttpServletRequest.
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    ContextHelperUtil contextHelperUtil;

    @Value("${app.jwt.header}")
    private String tokenRequestHeader;

    @Value("${app.jwt.header.prefix}")
    private String tokenRequestHeaderPrefix;

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        First we need to extract Authorization header from the ServletRequest.
//        Getting the token that certifies whether the user is authenticated or not:
        final String header = request.getHeader("Authorization");
        String jwtToken = null;
        String userEmail = null;

        if (header != null && header.startsWith("Bearer ")) {
//         "beginIndex": 7 will cut off the "Bearer " part at the head of the JWT Token and return the token:
            jwtToken = header.substring(7);
            try {
                userEmail = jwtUtil.getUserNameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                LOGGER.info("Unable to get JWT token");
            } catch (ExpiredJwtException e) {
                LOGGER.info("Jwt token is expired");
            }
        } else if (header == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // Status code (401) indicating that the request requires HTTP authentication.
        }
        else {
            LOGGER.info("Jwt token does not start with Bearer");
        }

        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
            if (jwtUtil.validateToken(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        } else {
            LOGGER.warn("JWT authentication failed");
        }
//        At that point, we have to continue the filter chain:
        filterChain.doFilter(request, response);
    }


    /**
     * Filter the incoming request for a valid token in the request header
     */
//    TODO: Solution 2 - exemple projet "Jwt-Spring Security - JPA"
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
//                                    FilterChain filterChain) throws ServletException, IOException {
//        try {
//            String jwt = getJwtFromRequest(request);
//
//            if (StringUtils.hasText(jwt) && jwtUtil.validateToken(jwt)) {
//                Long userId = jwtUtil.getUserIdFromJWT(jwt);
//                UserDetails userDetails = userDetailsService.loadUserById(userId);
//                UserDto userDto = userDetailsService.getUserService().getUserById(userId);
//                List<GrantedAuthority> authorities = userDetailsService.computeGrantedAuthorities(userDto);
//                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, jwt, authorities);
//                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//            }
//        } catch (Exception ex) {
//            LOGGER.error("Failed to set user authentication in security context: ", ex);
//            throw ex;
//        }
//
//        filterChain.doFilter(request, response);
//    }
//
//    /**
//     * Extract the token from the Authorization request header
//     */
//    private String getJwtFromRequest(HttpServletRequest request) {
//        String bearerToken = request.getHeader(tokenRequestHeader);
//        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(tokenRequestHeaderPrefix)) {
//            LOGGER.info("Extracted Token: " + bearerToken);
//            return bearerToken.replace(tokenRequestHeaderPrefix, "");
//        }
//        return null;
//    }
//
}
