package com.naderaria.identity.service;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface CustomUserDetailService  {

    UserDetailsService userDetailService();

}
