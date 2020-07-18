package com.musicinfofinder.musicinfofinderservice.models.response.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * Object to model Genius response to search API. Represents each one of the positive results. A search response
 * can contain multiple hits.
 */
@Data
public class Hit {
    @JsonProperty("highlights")
    private List<String> highlights;
    @JsonProperty("type")
    private HitType type;
    @JsonProperty("result")
    private Result result;
}
