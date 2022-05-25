package com.simplon.pixelartback.facade.security;

import com.simplon.pixelartback.service.user.UserService;
import com.simplon.pixelartback.storage.dao.UserDao;
import com.simplon.pixelartback.storage.dto.UserDto;
import com.simplon.pixelartback.storage.entity.user.UserEntity;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

//This class is our custom AuthenticationProvider class implementing Spring's AuthenticationProvider.

// Adding this @ so Spring will scan this class automatically when loading the application, it will
// instantiate them and inject any specified dependencies into them, and
// inject them wherever needed. /source: https://www.baeldung.com/spring-component-annotation

@Component
public class AuthenticationProviderImpl extends AbstractUserDetailsServiceImpl implements AuthenticationProvider {

//    @Autowired  //TODO: probablement pas nécessaire, puisque AbstractUserDetailsServiceImpl is autowired from SecurityConfig
//    UserService userService; //TODO: ez volt kijelolve amugy. Vagy ezt beveszem, vagy az "extends AbstractUserDetailsServiceImpl" mukodik helyette
//    UserDao userDao;

    @Autowired
    @Lazy
    PasswordEncoder passwordEncoder;

//    Check credential and return authentication objet
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        First we are having the email and password of the "authentication" object:
        String email = authentication.getName();   //.getName() returns a String
        String password = authentication.getCredentials().toString();   //.getCredentials() will return an Object so we are converting it to a String
//        Then we need to have the User from that email
        if(email == null) {
            throw new UsernameNotFoundException("User were not found");
        }
//        TODO: itt lehet, h "false" kell, vagy visszatenni az entity-t!!!
        UserDto user = getUserService().findByEmail(email, true);
//        val entity = userDao.findByEmail(email);

//        We also need to compare the passwords:
//        1st parameter is the raw password, the 2nd is the encoded one
        if(passwordEncoder.matches(password, user.getPassword())) {
//            If above condition is satisfied, then we return the Authentication Object,
//            the implementation of which is the "UsernamePasswordAuthenticationToken"
//            Parameters of the method: (Object principle, Object credentials, authorities)
            return new UsernamePasswordAuthenticationToken(email, password, new ArrayList<>());
        } else {
//            If the passwords are not matching:
            throw new BadCredentialsException("Invalid credentials, wrong password");
        }

    }

    @Override
    public boolean supports(Class<?> authentication) {
//      Here we check what the AuthenticationProvider supports.
//      Returns true if the AuthenticationProvider supports the indicated Authentication object.
//        source: https://docs.spring.io/spring-security/site/docs/4.0.x/apidocs/org/springframework/security/authentication/AuthenticationProvider.html
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
