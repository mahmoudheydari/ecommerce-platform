package com.naderaria.identity.dto.role_permission.request;

import jakarta.validation.constraints.NotNull;

public record ReqUpdatableRolePermissionDto(
        @NotNull(message = "reqUpdatableRolePermissionDto.id.NotNull") long id,
        @NotNull(message = "reqUpdatableRolePermissionDto.roleId.NotNull") long roleId,
        @NotNull(message = "reqUpdatableRolePermissionDto.permissionId.NotNull") long permissionId) {
}
