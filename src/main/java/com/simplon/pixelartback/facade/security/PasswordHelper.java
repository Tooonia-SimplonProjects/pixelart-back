package com.simplon.pixelartback.facade.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class PasswordHelper {
    @Autowired
    @Lazy
    private PasswordEncoder userPasswordEncoder;

    @Bean
    public PasswordEncoder userPasswordEncoder() {
        return new BCryptPasswordEncoder(8);
    }

    public String encodePassword(String password) {
        return userPasswordEncoder.encode(password);
    }

//    TODO: nem biztos, h a password-hoz, hanem majd a rajzhoz kellhet esetleg!
    public String base64Encode(String value) {
        return Base64.getEncoder().encodeToString(value.getBytes());
    }

}
