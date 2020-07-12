package com.musicinfofinder.musicinfofinderservice.models.response.search;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum HitType {
    @JsonProperty("song")
    SONG("song");

    String name;

    HitType(String name) {
        this.name = name;
    }
}
