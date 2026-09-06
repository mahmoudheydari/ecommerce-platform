package com.naderaria.cart.web.controller;


import com.naderaria.cart.web.dto.request.ReqCartItemDto;
import com.naderaria.cart.web.dto.response.ResCartItemDto;
import com.naderaria.cart.web.dto.response.ResCartItemPageItemDto;
import com.naderaria.cart.application.service.CartService;

import com.naderaria.commoncore.dto.request.PaginationDto;
import com.naderaria.commoncore.dto.response.PageResponse;
import com.naderaria.commoncore.util.WebUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ecom/cart")
public class CartController {

    private final CartService cartService;

    @GetMapping("/items")
    @PreAuthorize("hasRole('User') and hasPermission('CartItem','read')")
    public ResponseEntity<PageResponse<ResCartItemPageItemDto>> getCartItems(
            @RequestParam(name = "pageNumber", defaultValue = "1") int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "30") int pageSize,
            @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(name = "sortAscending", defaultValue = "true") boolean sortAscending) {

        PaginationDto paginationDto = new PaginationDto(pageNumber,pageSize,sortAscending,new String[]{sortBy});
        return ResponseEntity.ok(cartService.getCartItems(paginationDto));
    }

    @GetMapping("/item/{id}")
    @PreAuthorize("hasRole('User') and hasPermission('CartItem','read')")
    public ResponseEntity<ResCartItemDto> getCard(@PathVariable("id") Long id) {
        ResCartItemDto resCartItemDto = cartService.getCartItem(id);
        return ResponseEntity.ok(resCartItemDto);
    }

    @PostMapping("/item")
    @PreAuthorize("hasRole('User') and hasPermission('CartItem','write')")
    public ResponseEntity<ResCartItemDto> addItem(@RequestBody @Validated ReqCartItemDto reqCartItemDto) {
        ResCartItemDto resCartItem = cartService.addItem(reqCartItemDto);
        return ResponseEntity.created(WebUtil.createURI("/ecom/cart/item/",resCartItem.id())).body(resCartItem);
    }

    @DeleteMapping("/item/{id}")
    @PreAuthorize("hasRole('User') and hasPermission('CartItem','delete')")
    public ResponseEntity<Void> deleteItem(@PathVariable("id") Long id) {
        cartService.deleteItem(id);
        return ResponseEntity.ok().build();
    }
}