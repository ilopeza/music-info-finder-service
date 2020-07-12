package com.musicinfofinder.musicinfofinderservice.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.musicinfofinder.musicinfofinderservice.models.response.search.SearchResponse;
import lombok.Data;

@Data
public class BaseGeniusResponse {
    @JsonProperty("meta")
    private Meta meta;
    @JsonProperty("response")
    private SearchResponse response;
}
