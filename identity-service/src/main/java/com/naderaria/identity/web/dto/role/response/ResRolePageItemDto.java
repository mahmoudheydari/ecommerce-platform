package com.naderaria.identity.web.dto.role.response;

import com.naderaria.commoncore.dto.response.PageItem;

public record ResRolePageItemDto(
        Long id,
        String groupName,
        String title,
        String description)
        implements PageItem {
}
