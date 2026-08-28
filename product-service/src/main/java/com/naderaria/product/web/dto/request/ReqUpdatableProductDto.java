package com.naderaria.product.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;

public record ReqUpdatableProductDto(
        @NotNull(message = "reqUpdatableProductDto.id.notNull")
        Long id,
        @NotBlank(message = "reqUpdatableProductDto.name.notBlank")
        @Size(min = 3, max = 50, message = "reqUpdatableProductDto.name.size")
        String name,
        @NotBlank(message = "reqUpdatableProductDto.slug.notBlank")
        String slug,
        @NotBlank(message = "reqUpdatableProductDto.description.notBlank")
        @Size(min = 3, max = 50, message = "reqUpdatableProductDto.description.size")
        String description,
        @NotNull(message = "reqUpdatableProductDto.categoryId.NotNull")
        Long categoryId,
        @NotBlank(message = "reqUpdatableProductDto.statusType.NotBlank")
        String statusType,
        @Validated
        ReqPriceDto price,
        @Validated
        ReqInventoryDto inventory) {
}