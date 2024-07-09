package com.warungsaham.warungsahamappapi.global.exception;


import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.warungsaham.warungsahamappapi.global.response.ErrorResponse;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;


@ControllerAdvice
public class GlobalResponseExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalResponseExceptionHandler.class);


    //TODO : refactor untuk tidak pake overide pada MethodArgumentNotValidException
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, WebRequest request) {

        Map<String,String> errorMap = new HashMap<>();
        for (ObjectError error : ex.getAllErrors()) {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errorMap.put(fieldName, errorMessage);

            LOG.error("Error Field : {}, Cause : {}", fieldName , errorMessage);
            
        }

        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage("Invalid Request");
        errorResponse.setErrors(errorMap);

        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<ErrorResponse> handleException(ConstraintViolationException ex){
        LOG.debug(ex.getClass().getName());
        LOG.error("Constraint violation occurred: {}", ex.getMessage());
        
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            LOG.error("Error Field : {}, Cause : {}", violation.getPropertyPath(), violation.getMessage());
        }

        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
        errorResponse.setMessage(ex.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }


    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<ErrorResponse> handleException(NotFoundException ex){
        LOG.debug(ex.getClass().getName());
        LOG.error("Not Found: {}", ex.getMessage());

        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        errorResponse.setMessage(ex.getMessage());

        return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadCredentialsException.class)
    protected ResponseEntity<ErrorResponse> handleException(BadCredentialsException ex){
        LOG.debug(ex.getClass().getName());
        LOG.error("Bad Credential : {}", ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        errorResponse.setMessage("Invalid Credential");

        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }



    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<ErrorResponse> handleException(MethodArgumentTypeMismatchException ex){
        LOG.debug(ex.getClass().getName());
        LOG.error("Invalid param : {}", ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage("Invalid " + ex.getName() + " Format");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST); 
    }


    @ExceptionHandler(InvalidFieldException.class)
    protected ResponseEntity<ErrorResponse> handleException(InvalidFieldException ex){
        LOG.debug(ex.getClass().getName());
        LOG.error("Invalid field : {}", ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage(ex.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConflictException.class)
    protected ResponseEntity<ErrorResponse> handleException(ConflictException ex){
        LOG.debug(ex.getClass().getName());
        LOG.error("Conflict : {}", ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.CONFLICT.value());
        errorResponse.setMessage(ex.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(SQLException.class)
    protected ResponseEntity<ErrorResponse> handleSQLException(SQLException ex){
        LOG.debug(ex.getClass().getName());
        LOG.error("Database Error : {}", ex.getMessage());
        LOG.error("Error : ", ex);
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorResponse.setMessage("INTERNAL SERVER ERROR");

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<ErrorResponse> handleException(RuntimeException ex){
        LOG.debug(ex.getClass().getName());
        LOG.error("Internal Error : {}", ex.getMessage());
        LOG.error("Error : ", ex);
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorResponse.setMessage("INTERNAL SERVER ERROR");

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    
}
