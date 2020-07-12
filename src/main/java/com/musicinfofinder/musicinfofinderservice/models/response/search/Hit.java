package com.musicinfofinder.musicinfofinderservice.models.response.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Hit {
    @JsonProperty("highlights")
    private List<String> highlights;
    @JsonProperty("type")
    private HitType type;
    @JsonProperty("result")
    private Result result;
}
