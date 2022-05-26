package com.simplon.pixelartback.facade.security;

import com.simplon.pixelartback.service.user.UserService;
import com.simplon.pixelartback.storage.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service // TODO: no @ in Rudi!
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

//    This "loadUserByUsername" method allows to validate the User against our DB according to our own logic
//    In our case, the parameter that the method takes as a parameter is "email"
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UserDto user = userService.findByEmail(email, true);
        if (user == null) {
            throw new UsernameNotFoundException("Unknown email:" + email);
        }
//      TODO: passing the real authorities instead of an empty arraylist!!!
//        source: UserDetailServiceImpl for List<GrantedAuthority> <<< its method should be in AbstractUserDetailsServiceImpl !!!
        return new org.springframework.security.core.userdetails.User(email, user.getPassword(), new ArrayList<>());
    }
}
