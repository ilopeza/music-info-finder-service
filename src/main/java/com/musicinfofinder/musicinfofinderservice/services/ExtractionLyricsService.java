package com.musicinfofinder.musicinfofinderservice.services;

import com.musicinfofinder.musicinfofinderservice.models.response.search.genius.IResult;

public interface ExtractionLyricsService {

    String extractLyrics(IResult result);
}
