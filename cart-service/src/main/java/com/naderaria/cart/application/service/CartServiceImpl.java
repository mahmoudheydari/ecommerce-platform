package com.naderaria.cart.application.service;

import com.naderaria.cart.application.client.ProductClient;
import com.naderaria.cart.application.mapper.CartMapper;
import com.naderaria.cart.domain.entity.Cart;
import com.naderaria.cart.domain.entity.CartItem;
import com.naderaria.cart.domain.entity.CartStatus;
import com.naderaria.cart.domain.repository.CartItemRepository;
import com.naderaria.cart.domain.repository.CartRepository;
import com.naderaria.cart.web.dto.request.ReqCartItemDto;
import com.naderaria.cart.web.dto.response.ResCartItemDto;
import com.naderaria.cart.web.dto.response.ResCartItemPageItemDto;
import com.naderaria.commoncore.dto.request.PaginationDto;
import com.naderaria.commoncore.dto.response.PageResponse;
import com.naderaria.commondata.util.PageConvertor;
import com.naderaria.commonsecurity.utils.SecurityUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final SecurityUtils securityUtils;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    private final ProductClient productClient;
    private final CartMapper cartMapper;

    @Override
    @Transactional
    public PageResponse<ResCartItemPageItemDto> getCartItems(PaginationDto paginationDto) {
        Pageable pageable = PageConvertor.convertToPageable(paginationDto);
        Page<CartItem> cartItemPage = cartItemRepository.findAllByCartId(pageable, findCurrentCart().getId());
        return cartMapper.toResCartItemPageItemDto(cartItemPage);
    }


    @Override
    @Transactional
    public ResCartItemDto getCartItem(Long id) {
        CartItem cartItem = cartItemRepository.findById(id).orElseThrow(NullPointerException::new);
        return cartMapper.toResCartItemDto(cartItem);
    }

    @Override
    @Transactional
    public ResCartItemDto addItem(ReqCartItemDto reqCartItemDto) {
        CartItem cartItem = cartMapper.toCartItem(reqCartItemDto);
        productClient.checkProductQuantity(cartItem.getProductId(), cartItem.getQuantity());
        cartItem.setUnitPrice(productClient.getProductPrice(cartItem.getProductId()).finalPrice());
        Cart currentCart = findCurrentCart();
        currentCart.addItem(cartItem);
        if (currentCart.getId() != null) {
            currentCart.setUpdatedAt(LocalDateTime.now());
        }
        cartRepository.save(currentCart);
        productClient.decreaseProductQuantity(cartItem.getProductId(), cartItem.getQuantity());
        return cartMapper.toResCartItemDto(cartItem);
    }


    @Override
    @Transactional
    public void deleteItem(Long id) {
        Cart currentCart = findCurrentCart();
        CartItem currentCartItem = currentCart.getCartItemById(id);
        currentCart.removeItem(id);
        cartRepository.save(currentCart);
        productClient.increaseProductQuantity(currentCartItem.getProductId(), currentCartItem.getQuantity());
    }

    private Cart findCurrentCart() {
        Long userId = securityUtils.getCurrentUserId();
        return cartRepository.findByUserId(userId).orElse(
                Cart.builder()
                        .userId(userId)
                        .cartStatus(CartStatus.ACTIVE)
                        .createdAt(LocalDateTime.now())
                        .build()
        );
    }
}