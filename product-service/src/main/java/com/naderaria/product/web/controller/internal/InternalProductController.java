package com.naderaria.product.web.controller.internal;

import com.naderaria.product.application.service.ProductService;
import com.naderaria.product.web.dto.intrernal.ProductPriceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ecom/internal/product")
public class InternalProductController {

    private final ProductService productService;

    @GetMapping("/price/{id}")
    public ProductPriceDto getProductPrice(@PathVariable Long id) {
        return productService.getFinalPrice(id);
    }
}