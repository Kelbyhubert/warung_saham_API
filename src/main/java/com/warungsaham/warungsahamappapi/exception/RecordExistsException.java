package com.warungsaham.warungsahamappapi.exception;

import java.util.HashMap;

public class RecordExistsException extends RuntimeException {
    
    private HashMap<String,Boolean> validations;
    
    public RecordExistsException(String errorMessage, HashMap<String,Boolean> validations){
        super(errorMessage);
        this.validations = validations;
    }

    public HashMap<String, Boolean> getValidations() {
        return validations;
    }

}
