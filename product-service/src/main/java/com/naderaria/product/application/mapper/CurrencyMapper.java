package com.naderaria.product.application.mapper;

import com.naderaria.commoncore.dto.response.PageResponse;
import com.naderaria.commondata.util.PageConvertor;
import com.naderaria.product.domain.entity.Currency;
import com.naderaria.product.web.dto.request.ReqCurrencyDto;
import com.naderaria.product.web.dto.request.ReqUpdatableCurrencyDto;
import com.naderaria.product.web.dto.response.ResCurrencyDto;
import com.naderaria.product.web.dto.response.ResCurrencyPageItemDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CurrencyMapper {

    default PageResponse<ResCurrencyPageItemDto> toResCurrencyPageItemDto(Page<Currency> currencyPage) {
        return PageConvertor.toPageableDto(currencyPage, this::toResCurrencyPageItemDto);
    }

    ResCurrencyPageItemDto toResCurrencyPageItemDto(Currency currency);

    ResCurrencyDto toResCurrencyDto(Currency currency);

    Currency toCurrency(ReqCurrencyDto reqCurrencyDto);

    void update(ReqUpdatableCurrencyDto reqUpdatableCurrencyDto, @MappingTarget Currency oldCurrency);
}