package com.naderaria.identity.dto.permission.response;

import com.naderaria.commoncore.dto.response.PageItem;

public record ResPermissionPageItemDto(
        Long id,
        String operation,
        String targetType,
        String targetScope,
        String title)
        implements PageItem {
}
