package com.warungsaham.warungsahamappapi.global.exception;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.warungsaham.warungsahamappapi.global.response.ErrorResponse;

import jakarta.validation.ConstraintViolationException;


@ControllerAdvice
public class GlobalResponseExceptionHandler extends ResponseEntityExceptionHandler  {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalResponseExceptionHandler.class);

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        LOG.error("HTTP message not readable: {}", ex.getMessage());
        return new ResponseEntity<>(request, headers, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    protected ResponseEntity<ErrorResponse> handleException(ConstraintViolationException ex){
        LOG.error("Constraint violation occurred: {}", ex.getMessage());

        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
        errorResponse.setMessage(ex.getMessage());

        return new ResponseEntity<>(errorResponse, new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY);
    }


    protected ResponseEntity<ErrorResponse> handleException(NotFoundException ex){
        LOG.error("Not Found: {}", ex.getMessage());

        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        errorResponse.setMessage(ex.getMessage());

        return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
    }


    protected ResponseEntity<ErrorResponse> handleException(InvalidFieldException ex){
        LOG.error("Invalid field : {}", ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage(ex.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    protected ResponseEntity<ErrorResponse> handleException(Exception ex){
        LOG.error("Internal Error : {}", ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorResponse.setMessage("INTERNAL SERVER ERROR");

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    
}
