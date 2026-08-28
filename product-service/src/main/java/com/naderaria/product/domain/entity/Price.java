package com.naderaria.product.domain.entity;

import com.naderaria.commoncore.exception.BusinessException;
import com.naderaria.commoncore.exception.ErrorCode;
import com.naderaria.commondata.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@Table(name = "tb_price")
@SequenceGenerator(name = "sequence-generator", sequenceName = "price_seq", allocationSize = 1)
@SuperBuilder
@NoArgsConstructor
public class Price extends BaseEntity {

    @Column(name = "amount")
    private BigDecimal amount;

    @Getter
    @Column(name = "discount")
    private Integer discount;

    @Getter
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_currency")
    private Currency currency;

    public final void changePrice(BigDecimal amount, Currency currency) {

        if (amount == null) throw new BusinessException(ErrorCode.InvalidPriceException);

        if (currency == null)
            throw new BusinessException(ErrorCode.InvalidCurrencyException);

        if (amount.signum() <= 0)
            throw new BusinessException(ErrorCode.InvalidPriceException);

        this.amount = amount;
        this.currency = currency;
    }

    public final void applyDiscount(Integer discount) {
        if (discount == null) throw new BusinessException(ErrorCode.DiscountCannotBeNullException);
        if (discount < 0) {
            throw new BusinessException(ErrorCode.DiscountCannotBeNegativeException);
        }
        if (discount > 100) {
            throw new BusinessException(ErrorCode.UnreasonableDiscountException);
        }
        this.discount = discount;
    }

    public final BigDecimal getFinalPrice() {
        return amount.subtract(
                amount.multiply(BigDecimal.valueOf(discount))
                        .divide(BigDecimal.valueOf(100), this.currency.getFractionDigits(), RoundingMode.HALF_UP)
        );
    }

    public final void removeDiscount() {
        this.discount = 0;
    }

    public final boolean hasDiscount() {
        if (discount == null) throw new BusinessException(ErrorCode.DiscountCannotBeNullException);
        return this.discount > 0;
    }

    public final void increasePrice(BigDecimal amount) {
        if (amount == null) throw new BusinessException(ErrorCode.InvalidPriceException);
        if (amount.signum() <= 0)
            throw new BusinessException(ErrorCode.InvalidPriceException);
        this.amount = this.amount.add(amount);
    }

    public final void decreasePrice(BigDecimal amount) {
        if (amount == null) throw new BusinessException(ErrorCode.InvalidPriceException);
        if (amount.signum() <= 0)
            throw new BusinessException(ErrorCode.InvalidPriceException);
        BigDecimal newAmount = this.amount.subtract(amount);

        if (newAmount.signum() <= 0) {
            throw new BusinessException(ErrorCode.InvalidPriceException);
        }

        this.amount = newAmount;
    }

}