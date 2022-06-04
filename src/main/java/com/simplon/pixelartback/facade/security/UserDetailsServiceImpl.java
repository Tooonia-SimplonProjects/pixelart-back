package com.simplon.pixelartback.facade.security;

import com.simplon.pixelartback.storage.dto.UserDto;
import com.simplon.pixelartback.storage.dto.UserGetDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This "loadUserByUsername" method allows to validate the User against our DB according to our own logic.
 * In our case, the parameter that the method takes in as a parameter is "email"
 */

@Service
public class UserDetailsServiceImpl extends AbstractDetailServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UserDto user = getUserService().findByEmail(email, true);
        if (user == null) {
            throw new UsernameNotFoundException("Unknown email:" + email);
        }
        List<GrantedAuthority> grantedAuthorities = computeGrantedAuthorities(user);
        return new org.springframework.security.core.userdetails.User(email, user.getPassword(), grantedAuthorities);
    }
}