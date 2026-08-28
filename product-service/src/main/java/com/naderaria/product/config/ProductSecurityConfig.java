package com.naderaria.product.config;

import com.naderaria.commonsecurity.config.SharedSecurityConfigurer;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class ProductSecurityConfig {

    private final SharedSecurityConfigurer sharedSecurityConfigurer;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        sharedSecurityConfigurer.configure(http);

        http.authorizeHttpRequests(auth -> auth

                .requestMatchers(HttpMethod.GET,
                        "/ecom/products/**")
                .permitAll()

                .requestMatchers("/ecom/products/**")
                .hasRole("ADMIN")

                .anyRequest()
                .authenticated());

        return http.build();
    }

}