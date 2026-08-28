package com.naderaria.product.application.service;

import com.naderaria.commoncore.dto.request.PaginationDto;
import com.naderaria.commoncore.dto.response.PageResponse;
import com.naderaria.product.web.dto.request.ReqCategoryDto;
import com.naderaria.product.web.dto.request.ReqUpdatableCategoryDto;
import com.naderaria.product.web.dto.response.ResCategoryDto;
import com.naderaria.product.web.dto.response.ResCategoryPageItemDto;

public interface CategoryService {

    PageResponse<ResCategoryPageItemDto> getCategories(PaginationDto paginationDto);

    ResCategoryDto getCategory(Long id);

    ResCategoryDto save(ReqCategoryDto reqCategoryDto);

    void update(ReqUpdatableCategoryDto reqUpdatableCategoryDto);

    void delete(long id);

}