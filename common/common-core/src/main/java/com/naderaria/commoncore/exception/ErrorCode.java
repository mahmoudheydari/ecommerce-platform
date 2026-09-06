package com.naderaria.commoncore.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    //Common
    UNAUTHORIZED("unAuthorized",401),
    ACCESS_DENIED("accessDenied",403),
    ResourceNotFoundException("data_not_found", 404),
    DataReferencedException("data_referenced_exception", 304),
    DuplicateDataException("duplicate_data_exception", 409),
    ForbiddenException("Forbidden_exception", 403),
    SaveInDatabaseException("save_in_database_exception", 500),
    ValidationException("Validation_exception", 422),
    CreateUrlException("Create_url_exception",500),
    RequestContainsIncorrectData("RequestContainsIncorrectData",400),
    SQLException("SQLException",500),

    //Identity Service

    //Product Service
    CategoryInUseException("Category_in_use_exception",400),
    CategoryNotFoundException("CategoryNotFoundException",404),
    CurrencyInUseException("CurrencyInUseException",400),
    CurrencyNotFoundException("CurrencyNotFoundException",404),
    DiscountCannotBeNegativeException("DiscountCannotBeNegativeException",400),
    DiscountCannotBeNullException("DiscountCannotBeNullException",422),
    InvalidCategoryException("InvalidCategoryException",400),
    InvalidCurrencyException("InvalidCurrencyException",400),
    InvalidInventoryQuantityException("InvalidInventoryQuantityException",400),
    InvalidPriceException("InvalidPriceException",400),
    NotEnoughInventoryException("NotEnoughInventoryException",400),
    ReleaseInventoryException("ReleaseInventoryException",500),
    ReserveInventoryException("ReserveInventoryException",500),
    UnreasonableDiscountException("UnreasonableDiscountException",400),
    ProductNotFoundException("ProductNotFoundException",404),

    //Cart Service
    CartItemNotFoundException("CartItemNotFoundException",404),
    CartEmptyException("CartEmptyException",404);
    private final String Code;

    private final int status;

    ErrorCode(String code, int status){
        this.Code = code;
        this.status = status;
    }

}