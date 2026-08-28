package com.naderaria.product.web.dto.response;

import com.naderaria.commoncore.dto.response.PageItem;
import com.naderaria.product.domain.entity.ProductStatusType;

import java.math.BigDecimal;

public record ResProductPageItemDto(
        Long id,
        String name,
        String description,
        String categoryName,
        BigDecimal price,
        ProductStatusType statusType,
        int stockQuantity) implements PageItem {
}