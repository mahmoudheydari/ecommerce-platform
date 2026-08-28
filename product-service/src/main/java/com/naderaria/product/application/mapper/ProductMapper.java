package com.naderaria.product.application.mapper;

import com.naderaria.commoncore.dto.response.PageResponse;
import com.naderaria.commondata.util.PageConvertor;
import com.naderaria.product.domain.entity.Product;
import com.naderaria.product.web.dto.request.ReqProductDto;
import com.naderaria.product.web.dto.request.ReqUpdatableProductDto;
import com.naderaria.product.web.dto.response.ResProductDto;
import com.naderaria.product.web.dto.response.ResProductPageItemDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR,uses = PriceMapper.class)
public interface ProductMapper {

    default PageResponse<ResProductPageItemDto> toResProductPageItemDto(Page<ResProductPageItemDto> productPage) {
        return PageConvertor.pageDtoListToPageableDto(productPage);
    }

    @Mapping(target = "categoryName", source = "category.name")
    @Mapping(target = "statusType", source = "statusType")
    ResProductPageItemDto toResProductPageItemDto(Product product);

    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "categoryTitle", source = "category.id")
    ResProductDto toResProductDto(Product product);

    @Mapping(target = "category.id", source = "categoryId")
    @Mapping(target = "statusType", source = "statusType")
    Product toProduct(ReqProductDto reqProductDto);

    void update(ReqUpdatableProductDto reqUpdatableProductDto, @MappingTarget Product product);
}