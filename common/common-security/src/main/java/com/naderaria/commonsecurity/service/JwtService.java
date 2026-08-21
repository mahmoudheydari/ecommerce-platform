package com.naderaria.commonsecurity.service;

import com.naderaria.commonsecurity.dto.CurrentUserResponse;
import com.naderaria.commonsecurity.dto.JwtTokenResponse;

public interface JwtService {

    String generateToken(CurrentUserResponse currentUserResponse);

    String extractUsername(String token);

    String generateRefreshToken(CurrentUserResponse currentUserResponse);

    boolean isTokenValid(String token, String username);

    JwtTokenResponse extractJwtTokenDot(String token);

}