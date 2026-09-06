package com.naderaria.cart.domain.entity;

import com.naderaria.commoncore.exception.BusinessException;
import com.naderaria.commoncore.exception.ErrorCode;
import com.naderaria.commondata.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "tb_cart")
@SequenceGenerator(name = "sequence-generator", sequenceName = "cart_seq", allocationSize = 1)
@Getter
@SuperBuilder
@NoArgsConstructor
public class Cart extends BaseEntity {

    @Column(name = "fk_user")
    private Long userId;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "cart_status")
    private CartStatus cartStatus;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "cart", orphanRemoval = true)
    private List<CartItem> cartItems;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Setter
    @Column(name = "update_at")
    private LocalDateTime updatedAt;

    public void addItem(CartItem cartItem) {
        if (this.cartItems == null) {
            this.cartItems = new ArrayList<>();
        }
        //cartItem.setCart(this);//TODO check cart
        this.cartItems.add(cartItem);
    }

    public void removeItem(Long id) {
        if(this.cartItems != null) {
            Optional<CartItem> cartItemOptional = cartItems.stream()
                    .filter(cartItem -> cartItem.getId().equals(id)).findFirst();
            if(cartItemOptional.isPresent()) {
                CartItem cartItem = cartItemOptional.get();
                cartItem.removeCurrentCart();
                cartItems.remove(cartItem);
            }
        }
    }

    public CartItem getCartItemById(Long id) {
        if(this.cartItems != null) {
            Optional<CartItem> cartItemOptional = cartItems.stream()
                    .filter(cartItem -> cartItem.getId().equals(id)).findFirst();
            return cartItemOptional.orElseThrow(BusinessException.supplier(ErrorCode.CartItemNotFoundException));
        }
        throw BusinessException.of(ErrorCode.CartEmptyException);
    }
}