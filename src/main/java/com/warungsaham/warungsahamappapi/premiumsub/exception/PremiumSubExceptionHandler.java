package com.warungsaham.warungsahamappapi.premiumsub.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.warungsaham.warungsahamappapi.error.response.ErrorResponse;

@ControllerAdvice
public class PremiumSubExceptionHandler {
    
    @ExceptionHandler
    public ResponseEntity<ErrorResponse<String>> handleException(PremiumSubNotFoundException ex){

        ErrorResponse<String> errorResponse = new ErrorResponse<String>();

        errorResponse.setData("");
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);

    }
}
