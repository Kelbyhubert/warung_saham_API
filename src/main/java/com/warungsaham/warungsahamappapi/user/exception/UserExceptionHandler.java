package com.warungsaham.warungsahamappapi.user.exception;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


import com.warungsaham.warungsahamappapi.user.dto.response.UserErrorResponse;

@ControllerAdvice
public class UserExceptionHandler {
    
    @ExceptionHandler
    public ResponseEntity<UserErrorResponse<String>> handleException(UserNotFoundException ex){

        UserErrorResponse<String> errorResponse = new UserErrorResponse<String>();

        errorResponse.setData("");
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler
    public ResponseEntity<UserErrorResponse<HashMap<String,Boolean>>> handleException(UserExistsException ex){
        UserErrorResponse<HashMap<String,Boolean>> errorResponse = new UserErrorResponse<HashMap<String,Boolean>>();

        errorResponse.setData(ex.getValidations());
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

    }
}
