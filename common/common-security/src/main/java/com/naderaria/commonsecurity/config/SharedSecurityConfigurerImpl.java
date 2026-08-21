package com.naderaria.commonsecurity.config;

import com.naderaria.commonsecurity.filter.JwtConfigurer;
import com.naderaria.commonsecurity.handler.CustomAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SharedSecurityConfigurerImpl implements SharedSecurityConfigurer {

    private final JwtConfigurer jwtConfigurer;
    private final AccessDeniedHandler accessDeniedHandler;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(eh -> {
                            eh.authenticationEntryPoint(customAuthenticationEntryPoint);
                            eh.accessDeniedHandler(accessDeniedHandler);
                        }
                );
        jwtConfigurer.configure(http);
    }
}