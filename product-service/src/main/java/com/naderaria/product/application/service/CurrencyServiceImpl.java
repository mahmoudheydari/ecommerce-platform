package com.naderaria.product.application.service;

import com.naderaria.commoncore.dto.request.PaginationDto;
import com.naderaria.commoncore.dto.response.PageResponse;
import com.naderaria.commoncore.exception.BusinessException;
import com.naderaria.commoncore.exception.ErrorCode;
import com.naderaria.commondata.util.PageConvertor;
import com.naderaria.product.application.mapper.CurrencyMapper;
import com.naderaria.product.domain.entity.Currency;
import com.naderaria.product.domain.repository.CurrencyRepository;
import com.naderaria.product.domain.repository.PriceRepository;
import com.naderaria.product.web.dto.request.ReqCurrencyDto;
import com.naderaria.product.web.dto.request.ReqUpdatableCurrencyDto;
import com.naderaria.product.web.dto.response.ResCurrencyDto;
import com.naderaria.product.web.dto.response.ResCurrencyPageItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRepository currencyRepository;
    private final CurrencyMapper currencyMapper;
    private final PriceRepository priceRepository;

    @Override
    @Transactional
    public PageResponse<ResCurrencyPageItemDto> getCurrencies(PaginationDto paginationDto) {
        Pageable pageable = PageConvertor.convertToPageable(paginationDto);
        Page<Currency> currencyPage = currencyRepository.findAll(pageable);
        return currencyMapper.toResCurrencyPageItemDto(currencyPage);
    }

    @Override
    @Transactional
    public ResCurrencyDto getCurrency(Long id) {
        return currencyMapper.toResCurrencyDto(
                currencyRepository.findById(id).orElseThrow(()-> BusinessException.of(ErrorCode.CurrencyNotFoundException))
        );
    }

    @Override
    @Transactional
    public ResCurrencyDto save(ReqCurrencyDto reqCurrencyDto) {
        Currency currency = currencyMapper.toCurrency(reqCurrencyDto);
        currencyRepository.save(currency);
        return currencyMapper.toResCurrencyDto(currency);
    }

    @Override
    @Transactional
    public void update(ReqUpdatableCurrencyDto reqUpdatableCurrencyDto) {
        Currency oldCurrency = currencyRepository.findById(reqUpdatableCurrencyDto.id())
                .orElseThrow(()-> BusinessException.of(ErrorCode.CurrencyNotFoundException));
        currencyMapper.update(reqUpdatableCurrencyDto, oldCurrency);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (priceRepository.existsByCurrencyId(id)) { throw new BusinessException(ErrorCode.CurrencyInUseException); }
        currencyRepository.deleteById(id);
    }
}