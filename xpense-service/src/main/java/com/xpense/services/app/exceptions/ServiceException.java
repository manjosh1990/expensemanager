package com.xpense.services.app.exceptions;

public class ServiceException extends RuntimeException {
    private int errorCode;
    private String message;

    public ServiceException() {

    }

    public ServiceException(int code, String message) {
        super(message);
        this.message = message;
        this.errorCode = code;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
