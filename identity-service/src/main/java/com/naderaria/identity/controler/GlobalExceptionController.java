package com.naderaria.identity.controler;

import com.naderaria.commoncore.dto.response.ErrorResponse;
import com.naderaria.commoncore.exception.DataReferencedException;
import com.naderaria.commoncore.exception.DuplicateDataException;
import com.naderaria.commoncore.exception.ResourceNofFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionController {


    @ExceptionHandler(value = DuplicateDataException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateDataException(DuplicateDataException exception, WebRequest request) {
       return ResponseEntity.badRequest().body( exception.getErrorResponse());
    }



    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorResponse> dataNotFoundHandler(NullPointerException exception) {


        ResourceNofFoundException resourceNofFoundException = new ResourceNofFoundException("Data is not exist,Request parameter is invalid");
        resourceNofFoundException.setStackTrace(exception.getStackTrace());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resourceNofFoundException.getErrorResponse());
    }

    @ExceptionHandler(DataReferencedException.class)
    public ResponseEntity<ErrorResponse> dataReferencedException(DataReferencedException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getErrorResponse());
    }



}
