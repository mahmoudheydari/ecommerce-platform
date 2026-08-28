package com.naderaria.identity.web.dto.role_permission.response;

import com.naderaria.identity.web.dto.permission.response.ResUpdatablePermissionDto;

public record ResRolePermissionDto(
        long id,
        long roleId,
        String groupName,
        String roleTitle,
        ResUpdatablePermissionDto permissionDto) {
}





