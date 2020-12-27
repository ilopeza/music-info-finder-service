package com.musicinfofinder.musicinfofinderservice.models.requests.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Request received by the service to search for lyrics.
 */
@Data
public class SearchLyricsRequest {

    @NotBlank
    @JsonProperty("artist_name")
    private String artist_name;

    @NotBlank
    @JsonProperty("track_name")
    private String track_name;

    @NotBlank
    @JsonProperty("album_name")
    private String album_name;
}
