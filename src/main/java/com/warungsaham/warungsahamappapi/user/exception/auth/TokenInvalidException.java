package com.warungsaham.warungsahamappapi.user.exception.auth;

public class TokenInvalidException extends RuntimeException {
    
    public TokenInvalidException(String errorMessage){
        super(errorMessage);
    }

    
}
