package com.naderaria.cart.application.service;

import com.naderaria.cart.web.dto.request.ReqCartItemDto;
import com.naderaria.cart.web.dto.response.ResCartItemDto;
import com.naderaria.cart.web.dto.response.ResCartItemPageItemDto;
import com.naderaria.commoncore.dto.request.PaginationDto;
import com.naderaria.commoncore.dto.response.PageResponse;

public interface CartService {

    PageResponse<ResCartItemPageItemDto> getCartItems(PaginationDto paginationDto);

    ResCartItemDto getCartItem(Long id);

    ResCartItemDto addItem(ReqCartItemDto reqCartItemDto);

    void deleteItem(Long id);

}