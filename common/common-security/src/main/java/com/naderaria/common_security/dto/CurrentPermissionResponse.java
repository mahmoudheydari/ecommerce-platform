package com.naderaria.common_security.dto;

public record CurrentPermissionResponse(String operation, String targetType, String title) {
}
