package com.naderaria.identity.web.dto.role_permission.response;

import com.naderaria.commoncore.dto.response.PageItem;

public record ResRolePermissionPageItemDto(
        Long id,
        String groupName,
        String roleTitle,
        String permissionTitle)
        implements PageItem {
}





