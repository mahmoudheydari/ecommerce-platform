package com.naderaria.identity.dto.permission.response;

public record ResUpdatablePermissionDto(
        Long id,
        String operation,
        String targetType,
        String targetScope,
        String title,
        String code) {
}
