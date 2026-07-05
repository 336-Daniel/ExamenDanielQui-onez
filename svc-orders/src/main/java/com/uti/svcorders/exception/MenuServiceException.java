package com.uti.svcorders.exception;

public class MenuServiceException extends RuntimeException{
    public MenuServiceException(String message) {
        super(message);
    }

    public MenuServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
