package com.naderaria.product.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ReqUpdatableCurrencyDto(
        @NotNull(message = "reqCurrencyDto.id,NotNull")
        Long id,
        @NotBlank(message = "reqCurrencyDto.code.NotBlank")
        @Size(min = 3, max = 5, message = "reqCurrencyDto.code.Size")
        String code,
        @NotBlank(message = "reqCurrencyDto.name.NotBlank")
        @Size(min = 3, max = 50, message = "reqCurrencyDto.name.Size")
        String name,
        String symbol,
        int fractionDigits) {
}