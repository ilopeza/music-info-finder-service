package com.musicinfofinder.musicinfofinderservice.models.response.search.genius;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Object to model the result of the search. Keeps all the important information about the lyrics, artist, album.
 */
@Data
public class GeniusResult implements IResult {
    @JsonProperty("annotation_count")
    private int annotationCount;
    @JsonProperty("api_path")
    private String apiPath;
    @JsonProperty("full_title")
    private String fullTitle;
    @JsonProperty("header_image_thumbnail_url")
    private String headerImageThumbnailUrl;
    @JsonProperty("header_image_url")
    private String headerImageUrl;
    @JsonProperty("id")
    private String id;
    @JsonProperty("lyrics_owner_id")
    private String lyricsOwnerId;
    @JsonProperty("lyrics_state")
    private String lyricsState;
    @JsonProperty("path")
    private String path;
    @JsonProperty("song_art_image_thumbnail_url")
    private String songArtImageThumbnailUrl;
    @JsonProperty("title")
    private String title;
    @JsonProperty("title_with_featured")
    private String titleWithFeatured;
    @JsonProperty("url")
    private String url;
    @JsonProperty("primary_artist")
    private PrimaryArtist primaryArtist;
}
