package com.naderaria.common_security.service;

import com.naderaria.common_security.dto.CurrentUserResponse;
import com.naderaria.common_security.dto.JwtTokenResponse;

public interface JwtService {

    String generateToken(CurrentUserResponse currentUserResponse);

    String extractUsername(String token);

    String generateRefreshToken(CurrentUserResponse currentUserResponse);

    boolean isTokenValid(String token, String username);

    JwtTokenResponse extractJwtTokenDot(String token);

}
