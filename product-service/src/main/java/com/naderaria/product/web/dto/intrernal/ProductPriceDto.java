package com.naderaria.product.web.dto.intrernal;

import java.math.BigDecimal;

public record ProductPriceDto(Long id, BigDecimal finalPrice) {
}