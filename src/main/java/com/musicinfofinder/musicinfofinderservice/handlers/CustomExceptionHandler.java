package com.musicinfofinder.musicinfofinderservice.handlers;

import com.musicinfofinder.musicinfofinderservice.exceptions.BadRequestException;
import com.musicinfofinder.musicinfofinderservice.exceptions.ClientException;
import com.musicinfofinder.musicinfofinderservice.exceptions.ValidationException;
import com.musicinfofinder.musicinfofinderservice.models.response.InfoFinderResponse;
import com.musicinfofinder.musicinfofinderservice.models.response.error.RegularErrorResponse;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.LinkedHashMap;
import java.util.Optional;

import static com.musicinfofinder.musicinfofinderservice.models.response.error.RegularErrorResponse.RegularErrorResponseBuilder.aRegularErrorResponse;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class CustomExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<RegularErrorResponse> handleValidationException(ValidationException exception) {
        val message = exception.getLocalizedMessage();
        final RegularErrorResponse regularErrorResponse = aRegularErrorResponse()
                .withMessage(message)
                .build();
        return new ResponseEntity(regularErrorResponse, BAD_REQUEST);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<RegularErrorResponse> handleBadRequestException(BadRequestException exception) {
        val message = exception.getLocalizedMessage();
        final RegularErrorResponse regularErrorResponse = aRegularErrorResponse()
                .withStatus(BAD_REQUEST)
                .withMessage(message)
                .build();
        return new ResponseEntity(regularErrorResponse, BAD_REQUEST);
    }

    @ExceptionHandler(ClientException.class)
    public ResponseEntity<InfoFinderResponse> handleClientException(ClientException exception) {
        final val infoFinderResponse = InfoFinderResponse.builder()
                .status(1000)
                .build();
        return ResponseEntity.ok(infoFinderResponse);
    }

    /**
     * Extracts an Optional object detailed in the keys param as a string separated by "."
     *
     * @param message Raw message in Json format
     * @param keys    Separated path to get the attribute from the message
     * @return Optional object with the desired value or Optional.empty() if the value does not exist.
     */
    public Optional<Object> extractFomJson(String message, String keys) {
        LinkedHashMap<String, Object> rawErrorMap;
        try {
            JSONParser parser = new JSONParser(message);
            rawErrorMap = parser.parseObject();
        } catch (ParseException e) {
            log.error("Could not parse the message {} with keys {}", message, keys);
            return Optional.empty();
        }
        return extractFomMap(rawErrorMap, keys);
    }

    private Optional<Object> extractFomMap(LinkedHashMap map, String keys) {
        Object result;
        String currentKey = StringUtils.substringBefore(keys, ".");
        if (!map.containsKey(currentKey)) {
            return Optional.empty();
        }
        result = map.get(currentKey);
        //get the keys for next round, if that's possible
        String remainingKeys = StringUtils.substringAfter(keys, ".");
        if (StringUtils.isBlank(remainingKeys)) {
            //if there are no more keys, this should be the result
            return Optional.of(result);
        }
        //if there are still keys to find but there are no more maps, there is an error
        //and should return empty
        if (!result.getClass().isAssignableFrom(LinkedHashMap.class)) {
            return Optional.empty();
        }
        return extractFomMap((LinkedHashMap) result, remainingKeys);
    }
}
