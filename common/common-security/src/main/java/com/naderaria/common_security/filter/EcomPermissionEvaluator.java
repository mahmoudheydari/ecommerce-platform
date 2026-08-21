package com.naderaria.common_security.filter;

import com.naderaria.common_security.dto.JwtTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Objects;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class EcomPermissionEvaluator implements PermissionEvaluator {

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        return getPermissions(authentication).anyMatch(ga -> ga.getAuthority()
                .equals(permission + "_" + targetDomainObject.toString()));


    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return getPermissions(authentication).anyMatch(ga -> ga.getAuthority()
                .equals(permission + "_" + targetType));


    }

    private Stream<? extends GrantedAuthority> getPermissions(Authentication authentication) {
        Objects.requireNonNull(authentication.getPrincipal());
        JwtTokenResponse principal = (JwtTokenResponse) authentication.getPrincipal();
        return principal.authorities().stream();

    }
}
