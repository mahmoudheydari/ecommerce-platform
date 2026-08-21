package com.naderaria.identity.dto.role_permission.request;

import jakarta.validation.constraints.NotNull;

public record ReqRolePermissionDto(
        @NotNull(message = "reqRolePermissionDto.roleId.NotNull") long roleId,
        @NotNull(message = "reqRolePermissionDto.permissionId.NotNull") long permissionId) {
}
