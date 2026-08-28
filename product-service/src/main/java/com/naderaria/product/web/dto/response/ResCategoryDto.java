package com.naderaria.product.web.dto.response;

public record ResCategoryDto(Long id, String name, String description, int sortOrder, boolean active) {
}