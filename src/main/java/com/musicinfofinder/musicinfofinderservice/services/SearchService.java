package com.musicinfofinder.musicinfofinderservice.services;

import com.musicinfofinder.musicinfofinderservice.models.requests.search.SearchLyricsRequest;
import com.musicinfofinder.musicinfofinderservice.models.response.BaseGeniusResponse;
import com.musicinfofinder.musicinfofinderservice.models.response.search.Result;

import java.util.Optional;

public interface SearchService {
    BaseGeniusResponse findLyrics(SearchLyricsRequest request);

    Optional<Result> findResult(SearchLyricsRequest request);

    Optional<String> extractLyrics(SearchLyricsRequest request);
}
