package com.warungsaham.warungsahamappapi.global.exception;


public class InvalidFieldException extends RuntimeException {

    public InvalidFieldException(String errorMessage){
        super(errorMessage);
    }
}
