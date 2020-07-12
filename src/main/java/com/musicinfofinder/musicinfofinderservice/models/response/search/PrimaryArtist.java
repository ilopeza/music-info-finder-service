package com.musicinfofinder.musicinfofinderservice.models.response.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PrimaryArtist {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("api_path")
    private String apiPath;
    @JsonProperty("header_image_url")
    private String headerImageUrl;
    @JsonProperty("image_url")
    private String image_url;
    @JsonProperty("is_meme_verified")
    private boolean is_meme_verified;
    @JsonProperty("is_verified")
    private boolean is_verified;
    @JsonProperty("name")
    private String name;
    @JsonProperty("url")
    private String url;
}
