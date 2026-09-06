package com.naderaria.commonsecurity.utils;

import com.naderaria.commoncore.exception.BusinessException;
import com.naderaria.commoncore.exception.ErrorCode;
import com.naderaria.commonsecurity.dto.JwtTokenResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtilsImpl implements SecurityUtils {

    @Override
    public Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal() == null) {
            throw new BusinessException(ErrorCode.UNAUTHORIZED);
        }
        Object principal = authentication.getPrincipal();
        if(principal instanceof JwtTokenResponse jwtTokenResponse){
            return jwtTokenResponse.id();
        }
        throw new BusinessException(ErrorCode.UNAUTHORIZED);
    }
}