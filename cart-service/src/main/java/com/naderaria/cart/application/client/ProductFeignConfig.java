package com.naderaria.cart.application.client;

import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;

public class ProductFeignConfig {

    @Bean
    public ErrorDecoder productErrorDecoder(ProductFeignErrorDecoder productFeignErrorDecoder) {
        return productFeignErrorDecoder;
    }

}