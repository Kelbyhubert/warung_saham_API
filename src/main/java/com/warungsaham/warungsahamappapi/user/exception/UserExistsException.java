package com.warungsaham.warungsahamappapi.user.exception;

import java.util.HashMap;

public class UserExistsException extends RuntimeException {
    
    private HashMap<String,Boolean> validations;
    
    public UserExistsException(String errorMessage, HashMap<String,Boolean> validations){
        super(errorMessage);
        this.validations = validations;
    }

    public HashMap<String, Boolean> getValidations() {
        return validations;
    }
}
