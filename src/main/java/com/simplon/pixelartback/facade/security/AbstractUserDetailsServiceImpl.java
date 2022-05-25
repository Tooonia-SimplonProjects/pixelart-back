package com.simplon.pixelartback.facade.security;

import com.simplon.pixelartback.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;

public class AbstractUserDetailsServiceImpl {

    @Autowired
    UserService userService;

    protected UserService getUserService() {
        return userService;
    }
}
