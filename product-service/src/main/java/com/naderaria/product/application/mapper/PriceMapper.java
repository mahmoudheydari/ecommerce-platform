package com.naderaria.product.application.mapper;

import com.naderaria.product.domain.entity.Price;
import com.naderaria.product.web.dto.request.ReqPriceDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface PriceMapper {

    @Mapping(target = "currency.id", source = "currencyId")
    Price toPrice(ReqPriceDto reqPriceDto);
}