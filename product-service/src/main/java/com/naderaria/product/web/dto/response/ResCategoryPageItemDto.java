package com.naderaria.product.web.dto.response;

import com.naderaria.commoncore.dto.response.PageItem;

public record ResCategoryPageItemDto(
        Long id,
        Long parentId,
        String parentName,
        String name,
        String description,
        boolean active,
        int sortOrder)
        implements PageItem {
}