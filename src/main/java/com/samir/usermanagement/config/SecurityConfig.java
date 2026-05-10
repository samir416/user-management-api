package com.samir.usermanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {

        System.out.println("SecurityConfig Loaded");

        http

                // Disable CSRF
                .csrf(csrf -> csrf.disable())

                // Authorization Rules
                .authorizeHttpRequests(auth -> auth

                        // Swagger Public
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**",
                                "/v3/api-docs",
                                "/swagger-resources/**",
                                "/webjars/**"
                        ).permitAll()

                        // Login API Public
                        .requestMatchers(
                                "/auth/login"
                        ).permitAll()

                        // Public GET APIs
                        .requestMatchers(
                                HttpMethod.GET,
                                "/users/**"
                        ).permitAll()

                        // Protected POST API
                        .requestMatchers(
                                HttpMethod.POST,
                                "/save"
                        ).authenticated()

                        // Protected PUT APIs
                        .requestMatchers(
                                HttpMethod.PUT,
                                "/users/**"
                        ).authenticated()

                        // Protected DELETE APIs
                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/users/**"
                        ).authenticated()

                        // Everything Else Protected
                        .anyRequest()
                        .authenticated()
                )

                // Disable Default Login Form
                .formLogin(form -> form.disable())

                // Enable Basic Authentication
                .httpBasic(httpBasic -> {});

        return http.build();
    }
}