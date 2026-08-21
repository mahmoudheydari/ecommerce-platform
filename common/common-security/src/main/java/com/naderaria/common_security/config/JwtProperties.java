package com.naderaria.common_security.config;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "security.jwt")
public record JwtProperties(String signingKey,
                            String refreshSigningKey,
                            long accessTokenExpiration,
                            long refreshTokeExpiration) {
}
