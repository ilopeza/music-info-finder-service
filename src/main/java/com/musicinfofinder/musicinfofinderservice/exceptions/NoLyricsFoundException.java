package com.musicinfofinder.musicinfofinderservice.exceptions;

public class NoLyricsFoundException extends InfoFinderServiceException {
    public NoLyricsFoundException(String message) {
        super(message);
    }

    public NoLyricsFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
