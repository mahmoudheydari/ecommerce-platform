package com.naderaria.commoncore.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    //Common
    ResourceNotFoundException("data_not_found", 404),
    DataReferencedException("data_referenced_exception", 304),
    DuplicateDataException("duplicate_data_exception", 400),
    ForbiddenException("Forbidden_exception", 403),
    SaveInDatabaseException("save_in_database_exception", 500),
    ValidationException("Validation_exception", 400),
    CreateUrlException("Create_url_exception",500),

    //Identity Service

    //Product Service
    CategoryInUseException("Category_in_use_exception",401),
    CategoryNotFoundException("CategoryNotFoundException",404),
    CurrencyInUseException("CurrencyInUseException",401),
    CurrencyNotFoundException("CurrencyNotFoundException",404),
    DiscountCannotBeNegativeException("DiscountCannotBeNegativeException",401),
    DiscountCannotBeNullException("DiscountCannotBeNullException",401),
    InvalidCategoryException("InvalidCategoryException",401),
    InvalidCurrencyException("InvalidCurrencyException",401),
    InvalidInventoryQuantityException("InvalidInventoryQuantityException",401),
    InvalidPriceException("InvalidPriceException",401),
    NotEnoughInventoryException("NotEnoughInventoryException",401),
    ReleaseInventoryException("ReleaseInventoryException",500),
    ReserveInventoryException("ReserveInventoryException",500),
    UnreasonableDiscountException("UnreasonableDiscountException",401),
    ProductNotFoundException("ProductNotFoundException",404);

    private final String Code;

    private final int status;

    ErrorCode(String code, int status){
        this.Code = code;
        this.status = status;
    }

}