package com.musicinfofinder.musicinfofinderservice.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.musicinfofinder.musicinfofinderservice.models.response.search.genius.SearchResponse;
import lombok.Data;

/**
 * Generic response from Genius.
 */
@Data
public class BaseGeniusResponse {
    /**
     * Metadata about the status of the response.
     */
    @JsonProperty("meta")
    private Meta meta;

    /**
     * Response itself.
     */
    @JsonProperty("response")
    private SearchResponse response;
}
