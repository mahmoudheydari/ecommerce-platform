package com.naderaria.common_security.config;

import com.naderaria.common_security.filter.JwtAuthenticationFilter;
import com.naderaria.common_security.filter.JwtConfigurer;
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
