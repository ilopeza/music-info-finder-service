package com.musicinfofinder.musicinfofinderservice.models.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class InfoFinderResponse {
    private int status;
    private String lyrics;
}
