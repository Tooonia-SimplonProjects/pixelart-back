package com.simplon.pixelartback.facade.security;

import com.simplon.pixelartback.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

public class AbstractUserDetailsServiceImpl {

    @Autowired
    @Lazy
    UserService userService;

    protected UserService getUserService() {
        return userService;
    }
}
