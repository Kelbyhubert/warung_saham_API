package com.warungsaham.warungsahamappapi.premiumsub.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.warungsaham.warungsahamappapi.global.response.ErrorResponse;

@ControllerAdvice
public class PremiumSubExceptionHandler {
    
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(PremiumSubNotFoundException ex){

        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setMessage(ex.getMessage());
        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);

    }
}
