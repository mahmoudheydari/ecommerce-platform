package com.naderaria.common_security.filter;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

public interface JwtConfigurer {
    void configure(HttpSecurity http) throws Exception;
}
