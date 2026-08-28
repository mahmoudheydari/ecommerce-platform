package com.naderaria.identity.web.dto.permission.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ReqPermissionDto(
        @NotBlank(message = "reqPermissionDto.operation.NotBlank")
        @Size(min = 4, max = 10, message = "reqPermissionDto.operation.Size") String operation,
        @NotBlank(message = "reqPermissionDto.targetType.NotBlank")
        @Size(min = 3, max = 50, message = "reqPermissionDto.targetType.Size") String targetType,
        @NotBlank(message = "reqPermissionDto.targetScope.NotBlank")
        @Size(min = 3, max = 50, message = "reqPermissionDto.targetScope.Size") String targetScope,
        @NotBlank(message = "reqPermissionDto.title.NotBlank")
        @Size(min = 3, max = 255, message = "reqPermissionDto.title.Size") String title) {
}