package com.naderaria.commonsecurity.config;

import com.naderaria.commonsecurity.filter.JwtAuthenticationFilter;
import com.naderaria.commonsecurity.filter.JwtConfigurer;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtSecurityConfigurer implements JwtConfigurer {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Override
    public void configure(HttpSecurity http) {
        http.addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class
        );
    }
}