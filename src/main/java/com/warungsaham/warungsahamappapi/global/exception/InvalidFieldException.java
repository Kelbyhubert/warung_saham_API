package com.warungsaham.warungsahamappapi.global.exception;

import jakarta.servlet.ServletException;

public class InvalidFieldException extends RuntimeException {

    public InvalidFieldException(String errorMessage){
        super(errorMessage);
    }
}
