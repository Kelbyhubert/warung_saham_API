package com.warungsaham.warungsahamappapi.global.exception;



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
import lombok.extern.slf4j.Slf4j;


@Slf4j
@ControllerAdvice
public class GlobalResponseExceptionHandler extends ResponseEntityExceptionHandler  {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return new ResponseEntity<Object>(request, headers, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    protected ResponseEntity<Object> handleException(ConstraintViolationException ex){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(ex.getMessage());
        return new ResponseEntity<Object>(errorResponse, new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler({NotFoundException.class})
    protected ResponseEntity<ErrorResponse> handleException(NotFoundException ex){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(ex.getMessage());
        return new ResponseEntity<ErrorResponse>(errorResponse,HttpStatus.NOT_FOUND);
    }

    // @ExceptionHandler({Exception.class})
    // protected ResponseEntity<Object> handleException(Exception ex){
    //     logger.error(ex, ex);
    //     ErrorResponse<String> errorResponse = new ErrorResponse<String>();
    //     errorResponse.setData("");
    //     errorResponse.setMessage("Server Error");
    //     return new ResponseEntity<Object>(errorResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    // }

    
}
