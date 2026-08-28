package com.naderaria.product.web.dto.request;

import java.math.BigDecimal;

public record ReqPriceDto(BigDecimal amount, double discount, Long currencyId) {
}