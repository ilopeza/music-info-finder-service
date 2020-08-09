package com.musicinfofinder.musicinfofinderservice.models.response.search.genius;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.musicinfofinder.musicinfofinderservice.models.response.GeniusAbstractResponse;
import lombok.Data;

import java.util.List;

/**
 * Container of all the possible results when calling the search API.
 */
@Data
public class SearchResponse implements GeniusAbstractResponse {
    @JsonProperty("hits")
    List<Hit> hits;
}
