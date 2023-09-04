package com.warungsaham.warungsahamappapi.validation.service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;


@Service
public class ValidationService {

    private Validator validator;

    @Autowired
    public ValidationService(Validator validator){
        this.validator = validator;
    }

    public void validate(Object requestObject){
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(requestObject);

        if(constraintViolations.size() != 0){
            throw new ConstraintViolationException(constraintViolations);
        }
    }
}
