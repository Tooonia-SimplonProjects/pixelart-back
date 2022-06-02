package com.simplon.pixelartback.facade.security;

import com.simplon.pixelartback.service.user.UserService;
import com.simplon.pixelartback.storage.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * This "loadUserByUsername" method allows to validate the User against our DB according to our own logic.
 * In our case, the parameter that the method takes in as a parameter is "email"
 */

@Service // TODO: no @ in Rudi!
public class UserDetailsServiceImpl extends AbstractDetailServiceImpl implements UserDetailsService {

//    @Autowired
//    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UserDto user = getUserService().findByEmail(email, true);
        if (user == null) {
            throw new UsernameNotFoundException("Unknown email:" + email);
        }
//      TODO: passing the real authorities instead of an empty arraylist!!!
//        source: UserDetailServiceImpl for List<GrantedAuthority> <<< its method should be in AbstractUserDetailsServiceImpl !!!
        List<GrantedAuthority> grantedAuthorities = computeGrantedAuthorities(user);
        return new org.springframework.security.core.userdetails.User(email, user.getPassword(), grantedAuthorities);
//        return new org.springframework.security.core.userdetails.User(email, user.getPassword(), new ArrayList<>());
    }

    public UserDetails loadUserById(Long id) {
        UserDto user =  getUserService().getUserById(id);
//        LO.info("Fetched user : " + dbUser + " by " + id);
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), computeGrantedAuthorities(user));
//                .orElseThrow(() -> new UsernameNotFoundException("Couldn't find a matching user id in the database for " + id));
    }
}