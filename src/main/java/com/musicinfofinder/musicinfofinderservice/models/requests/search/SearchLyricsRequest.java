package com.musicinfofinder.musicinfofinderservice.models.requests.search;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

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

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }
}
