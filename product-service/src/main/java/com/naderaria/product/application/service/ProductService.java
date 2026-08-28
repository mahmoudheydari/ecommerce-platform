package com.naderaria.product.application.service;

import com.naderaria.commoncore.dto.request.PaginationDto;
import com.naderaria.commoncore.dto.response.PageResponse;
import com.naderaria.product.web.dto.intrernal.ProductPriceDto;
import com.naderaria.product.web.dto.request.ReqProductDto;
import com.naderaria.product.web.dto.request.ReqUpdatableProductDto;
import com.naderaria.product.web.dto.response.ResProductDto;
import com.naderaria.product.web.dto.response.ResProductPageItemDto;

public interface ProductService {

    PageResponse<ResProductPageItemDto> getProducts(PaginationDto paginationDto);

    ResProductDto getProduct(long id);

    ResProductDto save(ReqProductDto reqProductDto);

    void update(ReqUpdatableProductDto reqUpdatableProductDto);

    void delete(long id);

    ProductPriceDto getFinalPrice(Long id);
}