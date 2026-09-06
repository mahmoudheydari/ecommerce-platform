package com.naderaria.cart.domain.entity;

import com.naderaria.commondata.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Entity
@Table(name = "tb_cart_item")
@SequenceGenerator(name = "sequence-generator", sequenceName = "cart_item_seq", allocationSize = 1)
@Getter
@NoArgsConstructor
@SuperBuilder
public class CartItem extends BaseEntity {

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_cart")
    private Cart cart;

    @Column(name = "fk_product")
    private Long productId;

    @Column(name = "quantity")
    private Integer quantity;//تعداد

    @Setter
    @Column(name = "unit_price")
    private BigDecimal unitPrice;//قیمت فعلی

    @Transient
    private BigDecimal totalPrice;

    public CartItem(Cart cart){
        this.cart = cart;
    }

    public void removeCurrentCart(){
        this.cart = null;
    }
}