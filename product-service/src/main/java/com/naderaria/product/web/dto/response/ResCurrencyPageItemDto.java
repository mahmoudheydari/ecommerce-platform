package com.naderaria.product.web.dto.response;

import com.naderaria.commoncore.dto.response.PageItem;

public record ResCurrencyPageItemDto(Long id, String code, String name) implements PageItem {
}