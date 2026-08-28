package com.naderaria.product.web.dto.response;

import java.math.BigDecimal;

public record ResPriceDto(Long id, BigDecimal amount, Integer discount, Long currencyId, String currencyName) {
}