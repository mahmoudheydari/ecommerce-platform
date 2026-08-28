package com.naderaria.product.web.dto.intrernal;

import java.math.BigDecimal;

public record ProductSummeryDto(Long id, String name, BigDecimal price) {
}