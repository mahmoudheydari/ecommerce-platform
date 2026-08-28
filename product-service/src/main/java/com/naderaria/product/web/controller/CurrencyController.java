package com.naderaria.product.web.controller;

import com.naderaria.commoncore.dto.request.PaginationDto;
import com.naderaria.commoncore.dto.response.PageResponse;
import com.naderaria.commoncore.util.WebUtil;
import com.naderaria.product.application.service.CurrencyService;
import com.naderaria.product.web.dto.request.ReqCurrencyDto;
import com.naderaria.product.web.dto.request.ReqUpdatableCurrencyDto;
import com.naderaria.product.web.dto.response.ResCurrencyDto;
import com.naderaria.product.web.dto.response.ResCurrencyPageItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ecom")
public class CurrencyController {

    private final CurrencyService currencyService;

    @GetMapping("/currency")
    @PreAuthorize("hasRole('Admin') and hasPermission('Currency','read')")
    public ResponseEntity<PageResponse<ResCurrencyPageItemDto>> getAllCurrencies(
            @RequestParam(name = "pageNumber", defaultValue = "1") int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "30") int pageSize,
            @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(name = "sortAscending", defaultValue = "true") boolean sortAscending) {

        PaginationDto paginationDto = new PaginationDto(pageNumber, pageSize, sortAscending, new String[]{sortBy});
        return ResponseEntity.ok(currencyService.getCurrencies(paginationDto));
    }

    @GetMapping("/currency/{id}")
    @PreAuthorize("hasRole('Admin') and hasPermission('Currency','read')")
    public ResponseEntity<ResCurrencyDto> getCurrency(@PathVariable("id") Long id) {
        ResCurrencyDto resCurrencyDto = currencyService.getCurrency(id);
        return ResponseEntity.ok(resCurrencyDto);
    }

    @PostMapping("/currency")
    @PreAuthorize("hasRole('Admin') and hasPermission('Currency','write')")
    public ResponseEntity<ResCurrencyDto> save(@Validated @RequestBody ReqCurrencyDto reqCurrencyDto) {
        ResCurrencyDto resCurrencyDto = currencyService.save(reqCurrencyDto);
        return ResponseEntity.created(WebUtil.createURI("/ecom/category/", resCurrencyDto.id())).body(resCurrencyDto);
    }

    @PutMapping("/currency")
    @PreAuthorize("hasRole('Admin') and hasPermission('Currency','update')")
    public ResponseEntity<Void> update(@Validated @RequestBody ReqUpdatableCurrencyDto reqUpdatableCurrencyDto) {
        currencyService.update(reqUpdatableCurrencyDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/currency/{id}")
    @PreAuthorize("hasRole('Admin') and hasPermission('Currency','delete')")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        currencyService.delete(id);
        return ResponseEntity.ok().build();
    }
}