package com.musicinfofinder.musicinfofinderservice.exceptions;

public class ClientException extends InfoFinderServiceException {
    public ClientException(String message) {
        super(message);
    }

    public ClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
