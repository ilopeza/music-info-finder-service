package com.musicinfofinder.musicinfofinderservice.exceptions;

public class InfoFinderServiceException extends RuntimeException {
    public InfoFinderServiceException(String message) {
        super(message);
    }

    public InfoFinderServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
