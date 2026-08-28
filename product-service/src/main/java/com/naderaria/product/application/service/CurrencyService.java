package com.naderaria.product.application.service;

import com.naderaria.commoncore.dto.request.PaginationDto;
import com.naderaria.commoncore.dto.response.PageResponse;
import com.naderaria.product.web.dto.request.ReqCurrencyDto;
import com.naderaria.product.web.dto.request.ReqUpdatableCurrencyDto;
import com.naderaria.product.web.dto.response.ResCurrencyDto;
import com.naderaria.product.web.dto.response.ResCurrencyPageItemDto;

public interface CurrencyService {

    PageResponse<ResCurrencyPageItemDto> getCurrencies(PaginationDto paginationDto);

    ResCurrencyDto getCurrency(Long id);

    ResCurrencyDto save(ReqCurrencyDto reqCurrencyDto);

    void update(ReqUpdatableCurrencyDto reqUpdatableCurrencyDto);

    void delete(Long id);

}