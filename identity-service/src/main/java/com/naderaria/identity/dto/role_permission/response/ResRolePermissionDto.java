package com.naderaria.identity.dto.role_permission.response;

import com.naderaria.identity.dto.permission.response.ResUpdatablePermissionDto;

public record ResRolePermissionDto(
        long id,
        long roleId,
        String groupName,
        String roleTitle,
        ResUpdatablePermissionDto permissionDto) {
}





