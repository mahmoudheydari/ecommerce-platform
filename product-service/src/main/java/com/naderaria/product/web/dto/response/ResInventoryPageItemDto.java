package com.naderaria.product.web.dto.response;


import com.naderaria.commoncore.dto.response.PageItem;

public record ResInventoryPageItemDto(Long id, String productName, int quantity, int reservedQuantity)
        implements PageItem {
}