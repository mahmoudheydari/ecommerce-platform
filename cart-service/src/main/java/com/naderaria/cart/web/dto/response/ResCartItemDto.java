package com.naderaria.cart.web.dto.response;

import java.math.BigDecimal;

public record ResCartItemDto(
        Long id,
        String productName,
        Integer quantity,
        BigDecimal unitPrice) {
}