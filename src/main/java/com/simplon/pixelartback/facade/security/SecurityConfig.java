package com.simplon.pixelartback.facade.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.net.http.HttpHeaders;
import java.util.Arrays;

// Source: https://openclassrooms.com/fr/courses/5683681-secure-your-web-application-with-spring-security

// @Configuration makes that the official config file for the security filter chain
// Extending it to "WebSecurityConfigurerAdapter" adds security to our web layer
// @EnableWebSecurity also needs to be set so to designate that a web security config file, creating the security filter chain
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // enabling method level security
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//    @Autowired
//    @Lazy
//    private UserDetailsServiceImpl userDetailsService;

//    We are also registering our custom AuthenticationProvider within this SecurityConfig class:
//    @Autowired
//    private AuthenticationProviderImpl authenticationProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

//    Our custom filter needs to come BEFORE UsernamePasswordAuthenticationFilter in the configure() method.
    @Autowired
    JwtRequestFilter jwtRequestFilter;

    @Autowired
    @Lazy
    JwtService jwtService;

//    See: WebSecurityConfig class in "acl"
//    AuthenticationManagerBuilder handles the authentication ruleset, and customises the UserDetails
//    We are telling Spring Security to take "UserDetailsServiceImpl" class to validate the User
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        FIRST step of development: by using the default AuthenticationProvider,
//        that itself is using the UserDetailsService and the PasswordEncoder.
//        To the ".userDetailsService()" method we are passing our custom "userDetailsService",
//        And we also provide the password encoder.
        auth.userDetailsService(jwtService).passwordEncoder(passwordEncoder);

//        SECOND step of development: by adding our own custom AuthenticationProvider:
//        auth.authenticationProvider(authenticationProvider);
    }

//    The method we are using for the security filter chain, for customizing the HttpRequests.
//    We are then running all the Http requests through the security filter chain.
//    These requests need to go into the configure file, so we can run our security
//    filter chain ruleset on the requests; we add it as parameter to the method.
//    @Override means we want to run our ruleset, so overriding the normal Spring Security functions.
//    We are configuring "sessionManagement" to be "STATELESS", so Spring Security will not create sessions,
//    as it is the main reason we are using JWT Tokens.
    @Override
    public void configure(HttpSecurity http) throws Exception {
//        We are creating a form login (a default login page) in the security filter chain running on the set up ruleset.
        // TODO: /users-t levenni a permitAll-bol!!! csak postman testhez kell!
        http.cors().and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/users", "/api/user/{id}", "/api/signup", "/api/login",
                        "/api/pixelart-catalog", "/api/pixelart-simplelist", "/api/pixelart/{id}",
                        "/api/pixelart-simple/{id}", "/api/pixelart-by-user/{id}", "/authenticate").permitAll()
                .antMatchers("/api/my-profile/{uuid}", "/api/pixelart-create",
                        "/api/pixelart-edit/{id}").hasRole("USER")
//                        "/api/pixelart-edit/{id}").authenticated()
//                .antMatchers( "/api/user/{id}").hasRole("USER")
//                TODO: ha ezt a kov sort aktivalom, atnezni, h a korabbi engedelyeket kiknek kell adnom!!! :(video 7, 4:21
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class); //First comes our custom filter

//                .formLogin()
//                .and()
//                .httpBasic();
    }

//    TODO see: WebSecurityConfig files with "CorsConfigurationSource" if this does not work!
//    This is responsible for allowing requests from any application:
//    @Bean
//    CorsConfigurationSource corsConfigurationSource() {
//        final CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "OPTIONS", "PUT", "DELETE"));
//        configuration.addAllowedHeader("*");
//        configuration.addExposedHeader("Authorization");
//        configuration.addExposedHeader("X-TOKEN");
//        configuration.setAllowCredentials(true);
//
//        // Url autorisées
//        // 4200 pour les développement | 8080 pour le déploiement
//        configuration.setAllowedOrigins(Arrays.asList("*"));
//
//        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override // TODO: itt kicsit mas az ereeti kod, de ezt javasolta a SonarLint
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**")
//                        .allowedMethods("GET", "POST", "PUT", "DELETE")
//                        .allowedHeaders("*")
//                        .allowedOriginPatterns("*")
//                        .allowCredentials(true);
//            }
//        };
//    }
@Bean
public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(Arrays.asList("*"));
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
    configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
    configuration.setExposedHeaders(Arrays.asList("x-auth-token"));
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
}

//    Did not manage to work with this @Bean declaration here, within that class!
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder(8);
//    }

//    This authenticationManagerBean method will help us to authenticate the User
//    and will return an AuthenticationManager object.
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

//    Did not work either, needed the put back our own "UserDetailsServiceImpl"
//    inside the "configure(AuthenticationManagerBuilder auth)" method!
//    @Override
//    @Bean
//    public UserDetailsService userDetailsServiceBean() throws Exception {
//        return new UserDetailsServiceImpl();
//    }

    @Bean
    GrantedAuthorityDefaults grantedAuthorityDefaults() {
        // Remove the ROLE_ prefix
        return new GrantedAuthorityDefaults("");
    }
}
