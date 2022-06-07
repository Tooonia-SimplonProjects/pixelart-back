package com.simplon.pixelartback.facade.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

import java.util.Arrays;
import java.util.Collections;

/**
 * @Configuration makes that the official config file for the security filter chain.
 * Extending it to "WebSecurityConfigurerAdapter" adds security to our web layer.
 * @EnableWebSecurity also needs to be set in order to designate that a web security config file, creating the security filter chain.
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // enabling method level security
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    /**
     * AuthenticationManagerBuilder handles the authentication ruleset, and customises the UserDetails
     * We are telling Spring Security to take "UserDetailsServiceImpl" class to validate the User
     * See: WebSecurityConfig class in "acl"
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // We are using the default AuthenticationProvider.
        // To the ".userDetailsService()" method we are passing our custom "userDetailsService",
        // and we also provide the password encoder.
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    /**
     *  The method we are using for the security filter chain, for customizing the HttpRequests.
     *     We are then running all the Http requests through the security filter chain.
     *     These requests need to go into the configure file, so we can run our security
     *     filter chain ruleset on the requests; we add it as parameter to the method.
     *     @Override means we want to run our ruleset, so overriding the normal Spring Security functions.
     *     We are configuring "sessionManagement" to be "STATELESS", so Spring Security will not create sessions,
     *     as it is the main reason we are using JWT Tokens.
     *
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        // We are creating a form login (a default login page) in the security filter chain running on the set up ruleset.
        // TODO: /users-t levenni a permitAll-bol!!! csak postman teszthez kell!
        http.cors()
                .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/users", "/api/user/{id}", "/api/signup", "/api/login",
                        "/api/pixelart-catalog", "/api/pixelart-simplelist", "/api/pixelart/{id}",
                        "/api/pixelart-simple/{id}", "/api/pixelart-by-user/{id}", "/api/authenticate").permitAll()
                .antMatchers("/api/my-profile/{uuid}", "/api/pixelart-create",
                        "/api/pixelart-edit/{id}").hasRole("USER")
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

                http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class); //First comes our custom filter
//                .formLogin()
//                .and()
//                .httpBasic();

    }

//    TODO see: WebSecurityConfig files with "CorsConfigurationSource" if this does not work!
    /**
     * This is responsible for allowing requests from any application:
     */
//    This 1st solution did not work here, conflict as a WebMvcConfigurer.
//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**")
//                        .allowedMethods("GET", "POST", "PUT", "OPTIONS", "DELETE")
//                        .allowedHeaders("*")
//                        .exposedHeaders("WWW-Authenticate")
//                        .allowedOriginPatterns("*")
//                        .allowCredentials(true);
//            }
//        };
//    }

//    2nd solution:
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "OPTIONS", "PUT", "DELETE"));
        configuration.addAllowedHeader("*");
        configuration.addExposedHeader("Authorization");
//        configuration.setAllowedHeaders(Arrays.asList("Authorization", "content-type", "x-auth-token"));
//        configuration.addExposedHeader("X-TOKEN"); //TODO: elvileg ez nem kell!
        configuration.setAllowCredentials(true);

        // Authorized URLs
        // 4200, 8085
//        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedOriginPatterns(Collections.singletonList("*"));

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

//    3rd solution:
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//      CorsConfiguration configuration = new CorsConfiguration();
//      configuration.setAllowedOrigins(Arrays.asList("*"));
//      configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
//      configuration.setAllowedHeaders(Arrays.asList("Authorization", "content-type", "x-auth-token"));
//      configuration.setExposedHeaders(Arrays.asList("x-auth-token"));
//      UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//      source.registerCorsConfiguration("/**", configuration);
//      return source;
//   }

    /**
     * This authenticationManagerBean method will help us to authenticate the User
     *    and will return an AuthenticationManager object.
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * Method removing the the "ROLE_" prefix from user role's/authorities name as it is generated automatically by Spring
     * @return
     */
    @Bean
    GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults("");
    }
}
