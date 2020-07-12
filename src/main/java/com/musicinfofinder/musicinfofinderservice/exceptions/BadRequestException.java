package com.musicinfofinder.musicinfofinderservice.exceptions;

public class BadRequestException extends InfoFinderServiceException {
    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
