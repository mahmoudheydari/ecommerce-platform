package com.naderaria.commonsecurity.service;

import com.naderaria.commonsecurity.config.JwtProperties;
import com.naderaria.commonsecurity.dto.CurrentUserResponse;
import com.naderaria.commonsecurity.dto.JwtTokenResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final JwtProperties jwtProperties;

    @Override
    public String generateToken(CurrentUserResponse currentUserResponse) {
        return generateToken(extractClaims(currentUserResponse), currentUserResponse.getUsername(), jwtProperties.accessTokenExpiration());
    }

    private String generateToken(Map<String, Object> extraClaims, String username, long expiration) {
        return generateToken(extraClaims, username, expiration, jwtProperties.signingKey());
    }

    @Override
    public String generateRefreshToken(CurrentUserResponse currentUserResponse) {
        return "REFRESH_" + generateToken(extractClaims(currentUserResponse), currentUserResponse.getUsername(), jwtProperties.refreshTokenExpiration(), jwtProperties.refreshSigningKey());
    }

    private String generateToken(Map<String, Object> extraClaims, String username, long expiration, String signingKey) {
        return Jwts
                .builder()
                .claims()
                .add(extraClaims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .and()
                .signWith(getSigningKey(signingKey))
                .compact();
    }

    private Map<String, Object> extractClaims(CurrentUserResponse currentUserResponse) {
        List<String> currentUserAuthorities = currentUserResponse.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        Map<String, Object> claims = new HashMap<>();
        claims.put("authorities", currentUserAuthorities);
        claims.put("id", currentUserResponse.getId());
        return claims;
    }

    @Override
    public boolean isTokenValid(String token, String username) {
        final String extractTokenUsername = extractUsername(token);
        return (extractTokenUsername.equals(username)) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    @Override
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }


    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractClaims(token);
        return claimsResolver.apply(claims);
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    @Override
    public Claims parseToken(String token) {
        return extractClaims(token);
    }

    private Claims extractClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSigningKey(jwtProperties.signingKey()))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSigningKey(String key) {
        byte[] keyBytes = Decoders.BASE64.decode(key);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public JwtTokenResponse extractJwtTokenDot(String token) {
        Claims claims = extractClaims(token);
        List<String> authoritiesList = claims.get("authorities", List.class);
        List<SimpleGrantedAuthority> authorities =
                authoritiesList.stream()
                        .map(SimpleGrantedAuthority::new)
                        .toList();
        return new JwtTokenResponse(claims.get("id", Long.class), claims.getSubject(), authorities);
    }
}