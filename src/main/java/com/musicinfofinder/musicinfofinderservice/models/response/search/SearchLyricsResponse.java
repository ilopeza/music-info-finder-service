package com.musicinfofinder.musicinfofinderservice.models.response.search;

import lombok.Builder;
import lombok.Data;

/**
 * Represents the lyrics found  for the artist.
 */

@Data
@Builder
public class SearchLyricsResponse {
    private String lyrics;
}
