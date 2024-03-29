package com.warungsaham.warungsahamappapi.user.exception.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.warungsaham.warungsahamappapi.global.response.ErrorResponse;

@ControllerAdvice
public class TokenExceptionHandler {
    

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(TokenInvalidException ex){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        errorResponse.setMessage(ex.getMessage());
        
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(CredentialException ex){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        errorResponse.setMessage(ex.getMessage());
        
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }
}
