package com.naderaria.product.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ReqCategoryDto(
        @NotBlank(message = "reqCategoryDto.name.NotBlank")
        @Size(min = 3, message = "reqCategoryDto.name.Size")
        String name,
        String description,
        @NotNull(message = "reqCategoryDto.sortOrder.NotNull")
        Integer sortOrder,
        boolean active,
        Long parentId) {
}