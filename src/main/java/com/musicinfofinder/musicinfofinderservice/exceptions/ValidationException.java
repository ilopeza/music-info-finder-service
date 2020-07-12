package com.musicinfofinder.musicinfofinderservice.exceptions;

public class ValidationException extends InfoFinderServiceException {
    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
