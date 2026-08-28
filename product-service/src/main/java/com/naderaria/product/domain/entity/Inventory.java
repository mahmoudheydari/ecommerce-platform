package com.naderaria.product.domain.entity;

import com.naderaria.commoncore.exception.BusinessException;
import com.naderaria.commoncore.exception.ErrorCode;
import com.naderaria.commondata.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "tb_inventory")
@SequenceGenerator(name = "sequence-generator", sequenceName = "invt_seq", allocationSize = 1)
@SuperBuilder
@NoArgsConstructor
public class Inventory extends BaseEntity {

    @Getter
    @Column(name = "quantity")
    private int quantity;

    @Getter
    @Column(name = "reserved_quantity")
    private int reservedQuantity;// تعداد کالاهای رزرو شده

    public Integer getAvailableQuantity() {
        return this.quantity - this.reservedQuantity;
    }

    public final void increase(int quantity) {
        if (quantity <= 0) {
            throw new BusinessException(ErrorCode.InvalidInventoryQuantityException);
        }
        this.quantity = (this.quantity + quantity);
    }

    public final void decrease(int quantity) {
        if (quantity <= 0) {
            throw new BusinessException(ErrorCode.InvalidInventoryQuantityException);
        }
        if (quantity > this.getAvailableQuantity()) {
            throw new BusinessException(ErrorCode.NotEnoughInventoryException);
        }
        this.quantity = (this.quantity - quantity);
    }

    public final void reserve(int quantity) {
        if (quantity <= 0) {
            throw new BusinessException(ErrorCode.InvalidInventoryQuantityException);
        }
        if (this.getAvailableQuantity() < quantity) {
            throw new BusinessException(ErrorCode.ReserveInventoryException);
        }
        this.reservedQuantity = (this.reservedQuantity + quantity);
    }

    public final void release(int quantity) {
        if (quantity <= 0) {
            throw new BusinessException(ErrorCode.InvalidInventoryQuantityException);
        }
        if (this.reservedQuantity < quantity) {
            throw new BusinessException(ErrorCode.ReleaseInventoryException);
        }
        this.reservedQuantity = (this.reservedQuantity - quantity);
    }

    public final boolean isAvailable() {
        return this.getAvailableQuantity() > 0;
    }

    public final boolean isOutOfStock() {
        return this.getAvailableQuantity() <= 0;
    }

    public final void validateReservation(int quantity) {
        if (!hasEnough(quantity)) {
            throw new BusinessException(ErrorCode.ReserveInventoryException);
        }
    }

    public boolean hasEnough(int quantity) {
        return getAvailableQuantity() >= quantity;
    }
}