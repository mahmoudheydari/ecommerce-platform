package com.naderaria.commonsecurity.dto;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public record JwtTokenResponse(Long id, String username, Collection<? extends GrantedAuthority> authorities) {
}