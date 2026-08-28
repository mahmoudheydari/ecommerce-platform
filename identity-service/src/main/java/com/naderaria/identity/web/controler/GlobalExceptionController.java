package com.naderaria.identity.web.controler;

import com.naderaria.commoncore.dto.response.ErrorResponse;
import com.naderaria.commoncore.exception.BusinessException;
import com.naderaria.commoncore.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionController {

    @ExceptionHandler(value = BusinessException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateDataException(BusinessException exception, WebRequest request) {
        return ResponseEntity
                .status(exception.getErrorCode().getStatus())
                .body(exception.getErrorResponse());
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorResponse> dataNotFoundHandler(NullPointerException exception) {

        BusinessException resourceNofFoundException = BusinessException.of(ErrorCode.ResourceNotFoundException);
        resourceNofFoundException.setStackTrace(exception.getStackTrace());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resourceNofFoundException.getErrorResponse());
    }

}
