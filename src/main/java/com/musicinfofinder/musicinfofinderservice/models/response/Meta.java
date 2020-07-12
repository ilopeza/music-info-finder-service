package com.musicinfofinder.musicinfofinderservice.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Meta {
    @JsonProperty("status")
    private int status;
}
