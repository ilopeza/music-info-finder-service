package com.musicinfofinder.musicinfofinderservice.exceptions;

public class ExtractionLyricsException extends InfoFinderServiceException {
    public ExtractionLyricsException(String message) {
        super(message);
    }

    public ExtractionLyricsException(String message, Throwable cause) {
        super(message, cause);
    }
}
