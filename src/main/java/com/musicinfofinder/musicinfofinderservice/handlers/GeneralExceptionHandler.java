package com.musicinfofinder.musicinfofinderservice.handlers;

import com.musicinfofinder.musicinfofinderservice.models.response.error.RegularErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import static com.musicinfofinder.musicinfofinderservice.models.response.error.RegularErrorResponse.RegularErrorResponseBuilder.aRegularErrorResponse;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
@Slf4j
public class GeneralExceptionHandler {

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleGeneralException(Exception exception, WebRequest request) {
        String message = exception.getLocalizedMessage();

        final RegularErrorResponse regularErrorResponse = aRegularErrorResponse()
                .withMessage(message)
                .build();
        return new ResponseEntity(regularErrorResponse, INTERNAL_SERVER_ERROR);
    }
}
