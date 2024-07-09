package com.warungsaham.warungsahamappapi.user.exception.user;

import java.util.Map;

public class UserExistsException extends RuntimeException {
    
    private final Map<String,Boolean> validations;
    
    public UserExistsException(String errorMessage, Map<String,Boolean> validations){
        super(errorMessage);
        this.validations = validations;
    }

    public Map<String, Boolean> getValidations() {
        return validations;
    }
}
