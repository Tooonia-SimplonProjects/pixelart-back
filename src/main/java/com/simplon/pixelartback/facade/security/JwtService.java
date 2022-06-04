package com.simplon.pixelartback.facade.security;

import com.simplon.pixelartback.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Service class for authenticating a user and creating the token of that authentication
 */
@Service
public class JwtService extends AbstractDetailServiceImpl {

    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    public JwtResponse createJwtTokenAndAuthenticateUser(JwtRequest jwtRequest) throws Exception{

        String userEmail = jwtRequest.getEmail();
        String userPassword = jwtRequest.getPassword();
        authenticateUser(userEmail, userPassword);
        final UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
        String newGeneratedToken = jwtUtil.generateToken(userDetails);

        return new JwtResponse(newGeneratedToken);
    }

    public void authenticateUser(String userEmail, String userPassword) throws Exception {
        if(userEmail == null || userPassword == null) {
            throw new IllegalArgumentException("User email and password are obligatory");
        }
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userEmail, userPassword, new ArrayList<>()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid credentials: incorrect email or password", e);
        } catch (DisabledException e) {
            throw new DisabledException("User is disabled", e);
        }
    }
}
