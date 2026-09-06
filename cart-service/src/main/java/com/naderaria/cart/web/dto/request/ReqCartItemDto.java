package com.naderaria.cart.web.dto.request;

import jakarta.validation.constraints.NotNull;

public record ReqCartItemDto(
        @NotNull(message = "reqCartItemDto.productId.notNull")
        Long productId,
        @NotNull(message = "reqCartItemDto.quantity.notNull")
        Integer quantity) {
}