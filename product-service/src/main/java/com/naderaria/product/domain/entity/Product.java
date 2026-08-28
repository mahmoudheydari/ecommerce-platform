package com.naderaria.product.domain.entity;

import com.naderaria.commoncore.exception.BusinessException;
import com.naderaria.commoncore.exception.ErrorCode;
import com.naderaria.commondata.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Entity
@Table(name = "tb_product")
@SequenceGenerator(name = "sequence-generator", sequenceName = "prod_seq", allocationSize = 1)
@SuperBuilder
@NoArgsConstructor
public class Product extends BaseEntity {

    @Getter
    @Setter
    @Column(name = "name")
    private String name;

    @Getter
    @Setter
    @Column(name = "slug")
    private String slug;

    @Getter
    @Setter
    @Column(name = "description")
    private String description;

    @Getter
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_category")
    private Category category;

    @Getter
    @Enumerated(EnumType.STRING)
    @Column(name = "status_type")
    private ProductStatusType statusType;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "fk_price")
    private Price price;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "fk_inventory")
    private Inventory inventory;

    public final void changeCategory(Category category) {
        if (category == null) {
            throw new BusinessException(ErrorCode.InvalidCategoryException);
        }
        this.category = category;
    }

    public final void changePrice(BigDecimal amount, Currency currency) {
        if( price == null ) {
            throw new BusinessException(ErrorCode.InvalidPriceException);
        }
        this.price.changePrice(amount, currency);
    }

    public final void applyDiscount(Integer discount) {
        this.price.applyDiscount(discount);
    }

    public final void increaseInventory(int quantity) {
        this.inventory.increase(quantity);

        if (statusType == ProductStatusType.OUT_OF_STOCK && inventory.isAvailable()) {
            statusType = ProductStatusType.ACTIVE;
        }
    }

    public final void decreaseInventory(int quantity) {
        this.inventory.decrease(quantity);
        if (inventory.isOutOfStock()) {
            statusType = ProductStatusType.OUT_OF_STOCK;
        }
    }

    public final void reserveInventory(int quantity) {
        this.inventory.reserve(quantity);
    }

    public final void releaseInventory(int quantity) {
        this.inventory.release(quantity);
    }

    public final void unavailableProduct() {
        this.statusType = ProductStatusType.OUT_OF_STOCK;
    }

    public final Integer getAvailableQuantity() {
        return this.inventory.getAvailableQuantity();
    }

    public final BigDecimal getFinalPrice() {
        return this.price.getFinalPrice();
    }

}