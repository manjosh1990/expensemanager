package com.xpense.services.app.exceptions;


public class FileStorageException extends RuntimeException {
    public FileStorageException(String message) {
        super(message);
    }

    public FileStorageException(String message, Throwable exception){
        super(message,exception);
    }
}
