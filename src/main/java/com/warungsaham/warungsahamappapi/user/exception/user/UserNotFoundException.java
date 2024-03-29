package com.warungsaham.warungsahamappapi.user.exception.user;


public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String errorMessage){
        super(errorMessage);
    }
    
}
