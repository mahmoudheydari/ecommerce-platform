package com.naderaria.commonsecurity.dto;

public record CurrentPermissionResponse(String operation, String targetType, String title) {
}