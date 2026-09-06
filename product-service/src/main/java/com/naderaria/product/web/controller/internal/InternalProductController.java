package com.naderaria.product.web.controller.internal;

import com.naderaria.product.application.service.ProductService;
import com.naderaria.product.web.dto.intrernal.ProductPriceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ecom/product/internal")
public class InternalProductController {

    private final ProductService productService;

    @GetMapping("/checkProductQuantity/{productId}/{quantity}")
    public void checkProductQuantity(@PathVariable("productId") Long productId, @PathVariable("quantity") Integer quantity){
        productService.checkProductQuantity(productId,quantity);
    }

    @GetMapping("/price/{id}")
    public ProductPriceDto getProductPrice(@PathVariable Long id) {
        return productService.getFinalPrice(id);
    }

    @PutMapping("/decreaseProductQuantity/{productId}/{quantity}")
    public void decreaseProductQuantity(@PathVariable("productId") Long productId,@PathVariable("quantity") Integer quantity){
        productService.decreaseProductQuantity(productId,quantity);
    }

    @PutMapping(path="/increaseProductQuantity/{productId}/{quantity}")
    public void increaseProductQuantity(@PathVariable("productId") Long productId,@PathVariable("quantity") Integer quantity){
        productService.increaseProductQuantity(productId,quantity);
    }
}