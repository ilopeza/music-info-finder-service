package com.musicinfofinder.musicinfofinderservice.models.response.error;

import org.springframework.http.HttpStatus;

/**
 * Object to display a custom response most common exceptions are thrown.
 * See https://developer.spotify.com/documentation/web-api/#regular-error-object
 */
public class RegularErrorResponse {
    private HttpStatus status;
    private String message;

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static final class RegularErrorResponseBuilder {
        private HttpStatus status;
        private String message;

        private RegularErrorResponseBuilder() {
        }

        public static RegularErrorResponseBuilder aRegularErrorResponse() {
            return new RegularErrorResponseBuilder();
        }

        public HttpStatus getStatus() {
            return status;
        }

        public void setStatus(HttpStatus status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public RegularErrorResponseBuilder withStatus(HttpStatus status) {
            this.status = status;
            return this;
        }

        public RegularErrorResponseBuilder withMessage(String message) {
            this.message = message;
            return this;
        }

        public RegularErrorResponse build() {
            RegularErrorResponse regularErrorResponse = new RegularErrorResponse();
            regularErrorResponse.status = this.status;
            regularErrorResponse.message = this.message;
            return regularErrorResponse;
        }
    }
}
