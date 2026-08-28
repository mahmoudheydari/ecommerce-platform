package com.naderaria.product.web.dto.response;

public record ResProductDto(
        Long id,
        String name,
        String slug,
        String description,
        Long categoryId,
        String categoryTitle,
        String statusType,
        ResPriceDto price,
        ResInventoryDto inventory) {
}