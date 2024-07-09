package com.warungsaham.warungsahamappapi.user.exception.user;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.warungsaham.warungsahamappapi.user.dto.response.UserErrorResponse;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class UserExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(UserExceptionHandler.class);
    
    @ExceptionHandler({UserNotFoundException.class})
    protected ResponseEntity<UserErrorResponse<String>> handleException(UserNotFoundException ex){

        UserErrorResponse<String> errorResponse = new UserErrorResponse<>();

        errorResponse.setError(null);
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler({UserExistsException.class})
    protected ResponseEntity<UserErrorResponse<Map<String,Boolean>>> handleException(UserExistsException ex){

        LOG.error("User Exists : {}", ex.getValidations());
        UserErrorResponse<Map<String,Boolean>> errorResponse = new UserErrorResponse<>();
        
        errorResponse.setError(ex.getValidations());
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

    }
}
