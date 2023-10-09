package com.warungsaham.warungsahamappapi.global;

import java.util.HashMap;

import org.hibernate.mapping.Set;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.warungsaham.warungsahamappapi.error.errorresponse.ErrorResponse;
import com.warungsaham.warungsahamappapi.exception.RecordExistsException;

import jakarta.validation.ConstraintViolationException;


@ControllerAdvice
public class GlobalResponseExceptionHandler extends ResponseEntityExceptionHandler  {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return new ResponseEntity<Object>(request, headers, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    protected ResponseEntity<Object> handleValidationException(ConstraintViolationException ex){
        ErrorResponse<String> errorResponse = new ErrorResponse<String>();
        errorResponse.setData("");
        errorResponse.setMessage(ex.getMessage());
        return new ResponseEntity<Object>(errorResponse, new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler({RecordExistsException.class})
    protected ResponseEntity<Object> handleRecordExistsException(RecordExistsException ex){
        ErrorResponse<HashMap<String,Boolean>> errorResponse = new ErrorResponse<HashMap<String,Boolean>>();
        errorResponse.setData(ex.getValidations());
        errorResponse.setMessage(ex.getMessage());

        return new ResponseEntity<Object>(errorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
    

    
}
