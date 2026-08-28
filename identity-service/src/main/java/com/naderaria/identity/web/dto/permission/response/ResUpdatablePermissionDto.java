package com.naderaria.identity.web.dto.permission.response;

public record ResUpdatablePermissionDto(
        Long id,
        String operation,
        String targetType,
        String targetScope,
        String title,
        String code) {
}
