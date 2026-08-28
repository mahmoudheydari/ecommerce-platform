package com.naderaria.product.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;

public record ReqProductDto(
        @NotBlank(message = "reqProductDto.name.notBlank")
        @Size(min = 3, max = 50, message = "reqProductDto.name.size")
        String name,
        @NotBlank(message = "reqProductDto.slug.notBlank")
        String slug,
        @NotBlank(message = "reqProductDto.description.notBlank")
        @Size(min = 3, max = 50, message = "reqProductDto.description.size")
        String description,
        @NotNull(message = "reqProductDto.categoryId,NotNull")
        Long categoryId,
        @NotNull(message = "reqProductDto.statusTypeId,NotNull")
        String statusType,
        @Validated
        ReqPriceDto price,
        @Validated
        ReqInventoryDto inventory) {
}