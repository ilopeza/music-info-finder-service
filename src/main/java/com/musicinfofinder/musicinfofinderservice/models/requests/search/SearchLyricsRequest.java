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
    private String artistName;

    @NotBlank
    @JsonProperty("track_name")
    private String trackName;

    @NotBlank
    @JsonProperty("album_name")
    private String albumName;
}
