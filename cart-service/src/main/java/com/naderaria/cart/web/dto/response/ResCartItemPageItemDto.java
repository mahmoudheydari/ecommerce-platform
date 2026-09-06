package com.naderaria.cart.web.dto.response;


import com.naderaria.commoncore.dto.response.PageItem;

public record ResCartItemPageItemDto(
        Long id,
        Long productId,
        String productName,
        Integer quantity,
        double unitPrice
) implements PageItem {

}
