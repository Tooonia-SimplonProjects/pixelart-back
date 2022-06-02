package com.simplon.pixelartback.facade.security;

import com.simplon.pixelartback.service.user.UserService;
import com.simplon.pixelartback.storage.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class responible to call UserService allowing access to its methods,
 * and retrieve GrantedAuthority from a given User (UserDto)
 */
public class AbstractDetailServiceImpl {

    @Autowired
    @Lazy
    private UserService userService;

    protected List<GrantedAuthority> computeGrantedAuthorities(UserDto user) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        if (!CollectionUtils.isEmpty(Collections.singleton(user.getRole()))) {
                grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole().toString()));
            }
        return grantedAuthorities;
    }

    protected UserService getUserService() {
        return userService;
    }
}
