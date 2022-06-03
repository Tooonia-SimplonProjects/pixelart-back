package com.simplon.pixelartback.facade.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simplon.pixelartback.service.mapper.UserMapper;
import com.simplon.pixelartback.service.user.UserService;
import com.simplon.pixelartback.storage.dto.UserDto;
import com.simplon.pixelartback.storage.entity.user.UserEntity;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JwtService extends AbstractDetailServiceImpl { //TODO: jo otlet-e ezt extends?
//public class JwtService extends AbstractDetailServiceImpl implements UserDetailsService { //TODO: jo otlet-e ezt extends?

    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    UserService userService;

    @Autowired
    ContextHelperUtil contextHelperUtil;

    private final ObjectMapper mapper = new ObjectMapper();

    public JwtResponse createJwtToken(JwtRequest jwtRequest) throws Exception{
//    public JwtResponse createJwtToken(JwtRequest jwtRequest) throws Exception{
        String userEmail = jwtRequest.getEmail();
        String userPassword = jwtRequest.getPassword();
//    public JwtResponse createJwtToken(UserDto userDto) throws Exception{
//        String userEmail = userDto.getEmail();
//        String userPassword = userDto.getPassword();
        authenticateUser(userEmail, userPassword);
        final UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
//        final UserDto userDto = userService.findByEmail(authenticatedUser.getEmail(), false);
        String newGeneratedToken = jwtUtil.generateToken(userDetails);
//        UserDto user = getUserService().findByEmail(userEmail, true);
        return new JwtResponse(newGeneratedToken);
//        return new JwtResponse(user, newGeneratedToken);
    }

//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//
//        UserDto user = getUserService().findByEmail(email, true);
//        if (user == null) {
//            throw new UsernameNotFoundException("Unknown email:" + email);
//        }
////      TODO: passing the real authorities instead of an empty arraylist!!!
////        source: UserDetailServiceImpl for List<GrantedAuthority> <<< its method should be in AbstractUserDetailsServiceImpl !!!
//        List<GrantedAuthority> grantedAuthorities = computeGrantedAuthorities(user);
//        return new org.springframework.security.core.userdetails.User(email, user.getPassword(), grantedAuthorities);
////        return new org.springframework.security.core.userdetails.User(email, user.getPassword(), new ArrayList<>());
//    }

//    private void authenticate(String userEmail, String userPassword) throws Exception{
//        try {
//            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userEmail, userPassword));
//        } catch (DisabledException e) {
//            throw new Exception("User is disabled");
//        } catch(BadCredentialsException e) {
//            throw new Exception("Bad credentials from user");
//        }
//    }
public void authenticateUser(String userEmail, String userPassword) throws Exception {
        if(userEmail == null || userPassword == null) {
            throw new IllegalArgumentException("User email and password are obligatory");
        }
//        UserDto user = getUserService().findByEmail(userEmail, true);
//        val entity = userMapper.dtoToEntity(user);
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userEmail, userPassword, new ArrayList<>()));
//                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
//                    new UsernamePasswordAuthenticationToken(entity.getEmail(), entity.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

//            AuthenticatedUser authenticatedUser = mapper.convertValue(authentication.getPrincipal(), AuthenticatedUser.class);
//            final var authenticatedUser = mapper.convertValue(authentJtd.getAccount(), AuthenticatedUser.class);
//            contextHelperUtil.setAuthenticatedUser((AuthenticatedUser) authentication.getPrincipal());
//            AuthenticatedUser authenticatedUser = (AuthenticatedUser) authentication.getPrincipal();
//            return createJwtToken(authenticatedUser);
        } catch (BadCredentialsException e) {
            throw new Exception("Invalid credentials: incorrect email or password", e);
        } catch (DisabledException e) {
            throw new Exception("User is disabled", e);
        }

    }
}
