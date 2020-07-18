package com.musicinfofinder.musicinfofinderservice.models.response.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.musicinfofinder.musicinfofinderservice.models.response.GeniusAbstractResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
public class SearchResponse implements GeniusAbstractResponse {
    @JsonProperty("hits")
    List<Hit> hits;
}
