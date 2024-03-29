package com.warungsaham.warungsahamappapi.user.exception.auth;

public class CredentialException extends RuntimeException {
    
    public CredentialException(String errorMessage){
        super(errorMessage);
    }

}
