package com.hameed.inventario.config;

import com.hameed.inventario.filter.JwtRequestFilter;
import com.hameed.inventario.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    @Value("${cors.allowed-origins}")
    private String[] allowedOrigins;
    @Value("${cors.allowed-methods}")
    private String[] allowedMethods;
    @Value("${cors.allowed-headers}")
    private String[] allowedHeaders;

    @Autowired
    public WebSecurityConfig(UserDetailsService userDetailsService, JwtUtil jwtUtil) {
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(
                        authorize ->
                                authorize
                                        .requestMatchers("/v1/auth/login",
                                                "/openapi/**",
                                                "/actuator/**",          // Exclude Actuator Endpoints
                                                "/swagger-ui/**",        // Exclude Swagger UI paths
                                                "/v3/api-docs/**",       // Exclude OpenAPI spec paths
                                                "/swagger-resources/**", // Exclude Swagger resources
                                                "/webjars/**")           // Exclude webjars used by Swagger
                                        .permitAll()
                                        .anyRequest()
                                        .authenticated()
                )
                .csrf(CsrfConfigurer::disable)
                // I need to allow CORS for the front end to be able to access the back end
                .cors(cors -> cors.configurationSource(request -> {
                    var corsConfiguration = new org.springframework.web.cors.CorsConfiguration();
                    corsConfiguration.setAllowedOrigins(Arrays.asList(allowedOrigins));
                    corsConfiguration.setAllowedMethods(Arrays.asList(allowedMethods));
                    corsConfiguration.setAllowedHeaders(Arrays.asList(allowedHeaders));
                    return corsConfiguration;
                }))
//                .formLogin(Customizer.withDefaults()); // we should add CORS management and then add the front end login form here
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(new JwtRequestFilter(userDetailsService, jwtUtil), UsernamePasswordAuthenticationFilter.class);
//                .addFilter(new (new JwtAuthenticationFilter()))


        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}

