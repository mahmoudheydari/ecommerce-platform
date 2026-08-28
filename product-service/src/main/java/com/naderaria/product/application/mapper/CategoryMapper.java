package com.naderaria.product.application.mapper;

import com.naderaria.commoncore.dto.response.PageResponse;
import com.naderaria.commondata.util.PageConvertor;
import com.naderaria.product.domain.entity.Category;
import com.naderaria.product.web.dto.request.ReqCategoryDto;
import com.naderaria.product.web.dto.request.ReqUpdatableCategoryDto;
import com.naderaria.product.web.dto.response.ResCategoryDto;
import com.naderaria.product.web.dto.response.ResCategoryPageItemDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CategoryMapper {

    default PageResponse<ResCategoryPageItemDto> toReCategoriesPageItemDto(Page<Category> categoryPage) {
        return PageConvertor.toPageableDto(categoryPage, this::toResCategoryPageItemDto);
    }

    @Mapping(target = "parentId", source = "parent.id")
    @Mapping(target = "parentName", source = "parent.name")
    ResCategoryPageItemDto toResCategoryPageItemDto(Category category);

    ResCategoryDto toResCategoryDto(Category category);

    @Mapping(target = "parent", ignore = true)
    Category toCategory(ReqCategoryDto reqCategoryDto);

    Category toCategory(ReqUpdatableCategoryDto reqUpdatableCategoryDto);

    @Mapping(target = "parent", ignore = true)
    void update(ReqUpdatableCategoryDto reqUpdatableCategoryDto, @MappingTarget Category category);

}