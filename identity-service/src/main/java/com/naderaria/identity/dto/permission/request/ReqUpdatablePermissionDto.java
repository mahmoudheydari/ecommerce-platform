package com.naderaria.identity.dto.permission.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ReqUpdatablePermissionDto(
        @NotNull(message = "reqUpdatablePermissionDto.id.NotNull") long id,
        @NotBlank(message = "reqUpdatablePermissionDto.operation.NotBlank")
        @Size(min = 4, max = 10, message = "reqUpdatablePermissionDto.operation.Size") String operation,
        @NotBlank(message = "reqUpdatablePermissionDto.targetType.NotBlank")
        @Size(min = 3, max = 50, message = "reqUpdatablePermissionDto.targetType.Size") String targetType,
        @NotBlank(message = "reqUpdatablePermissionDto.targetScope.NotBlank")
        @Size(min = 3, max = 50, message = "reqUpdatablePermissionDto.targetScope.Size") String targetScope,
        @NotBlank(message = "reqUpdatablePermissionDto.title.NotBlank")
        @Size(min = 3, max = 255, message = "reqUpdatablePermissionDto.title.Size") String title) {
}
