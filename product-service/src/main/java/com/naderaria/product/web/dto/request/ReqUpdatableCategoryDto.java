package com.naderaria.product.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ReqUpdatableCategoryDto(
        @NotNull(message = "reqUpdatableCategoryDto.id.NotNull")
        Long id,
        @NotBlank(message = "reqCategoryDto.title.NotBlank")
        @Size(min = 3, message = "reqCategoryDto.title.Size")
        String name,
        String description,
        @NotNull(message = "reqCategoryDto.sortOrder.NotNull")
        Integer sortOrder,
        boolean active,
        Long parentId) {
}