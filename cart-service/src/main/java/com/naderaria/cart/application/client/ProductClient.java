package com.naderaria.cart.application.client;

import com.naderaria.cart.web.dto.internal.ResProductPriceDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(
        name = "product-service",
        url = "${product-service.url}",
        configuration = ProductFeignConfig.class)
public interface ProductClient {

    @GetMapping(path = "/ecom/product/internal/checkProductQuantity/{productId}/{quantity}")
    void checkProductQuantity(@PathVariable("productId") Long productId, @PathVariable("quantity") Integer quantity);

    @GetMapping(path = "/ecom/product/internal/price/{id}")
    ResProductPriceDto getProductPrice(@PathVariable("id") Long id);

    @PutMapping(path = "/ecom/product/internal/decreaseProductQuantity/{productId}/{quantity}")
    void decreaseProductQuantity(@PathVariable("productId") Long productId, @PathVariable("quantity") Integer quantity);

    @PutMapping(path = "/ecom/product/internal/increaseProductQuantity/{productId}/{quantity}")
    void increaseProductQuantity(@PathVariable("productId") Long productId, @PathVariable("quantity") Integer quantity);

}