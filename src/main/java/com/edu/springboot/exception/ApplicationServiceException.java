package com.edu.springboot.exception;

public class ApplicationServiceException extends RuntimeException {

    private final int status;
    private final String message;

    public ApplicationServiceException(int status, String message){
        super(message);
        this.status = status;
        this.message = message;
    }

    int getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
