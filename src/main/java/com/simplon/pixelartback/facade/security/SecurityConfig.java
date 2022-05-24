package com.simplon.pixelartback.facade.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

// Source: https://openclassrooms.com/fr/courses/5683681-secure-your-web-application-with-spring-security

// @Configuration makes that the official config file for the security filter chain
// Extending it to "WebSecurityConfigurerAdapter" adds security to our web layer
// @EnableWebSecurity also needs to be set so to designate that a web security config file, creating the security filter chain
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

//    We are also registering our custom AuthenticationProvider within this SecurityConfig class:


//    See: WebSecurityConfig class in "acl"
//    AuthenticationManagerBuilder handles the authentication ruleset, and customises the UserDetails
//    We are telling Spring Security to take "UserDetailsServiceImpl" class to validate the User
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        To the ".userDetailsService()" method we are passing the custom "userDetailsService",
//        And we also provide the password encoder
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

//    The method we are using for the security filter chain, for customizing the HttpRequests.
//    We are then running all the Http requests through the security filter chain.
//    These requests need to go into the configure file so we can run our security
//    filter chain ruleset on the requests, so we add it as parameter to the method.
//    @Override means we want to run our ruleset, so overriding the normal Spring Security functions.
    @Override
    public void configure(HttpSecurity http) throws Exception {
//        We are creating a form login (a default login page) in the security filter chain running on the set up ruleset.
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/users", "/api/user/{id}", "/api/signup", "/api/login").permitAll()
                .antMatchers("/api/my-user/{uuid}" ).authenticated()
//                .antMatchers("/api/users", "/api/user", "/api/my-user/*").permitAll()
//                .antMatchers( "/api/user/{id}").hasRole("USER")
//                TODO: ha ezt aktivalom, atnezni, h a korabbi engedelyeket kiknek kell adnom!!! :(video 7, 4:21
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .and()
                .httpBasic();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(8);
    }

//    This authenticationManagerBean method will help us to authenticate the User
//    and will return an AuthenticationManager object.
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
