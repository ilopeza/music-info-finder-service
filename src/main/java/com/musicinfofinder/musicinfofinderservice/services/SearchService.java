package com.musicinfofinder.musicinfofinderservice.services;

import com.musicinfofinder.musicinfofinderservice.models.requests.search.SearchLyricsRequest;
import com.musicinfofinder.musicinfofinderservice.models.response.BaseGeniusResponse;
import com.musicinfofinder.musicinfofinderservice.models.response.search.Result;
import com.musicinfofinder.musicinfofinderservice.models.response.search.SearchLyricsResponse;

import java.util.Optional;

/**
 * Service to find the lyrics in external service
 */
public interface SearchService {
    /**
     * Gets the complete raw response from the external service.
     *
     * @param request
     * @return Raw response from external service
     */
    BaseGeniusResponse getExternalSearchRawResponse(SearchLyricsRequest request);

    /**
     * Gets the raw response and filters the specific result.
     *
     * @param request
     * @return the result for the search request, if exists.
     * @throws com.musicinfofinder.musicinfofinderservice.exceptions.NoLyricsFoundException if no lyrics are found.
     */
    Optional<Result> filterResult(SearchLyricsRequest request);

    /**
     * Gets tht response from external service and transforms it into SearchLyricsResponse
     *
     * @param request
     * @return SearchLyricsResponse with the lyrics and metadata.
     * @throws com.musicinfofinder.musicinfofinderservice.exceptions.ValidationException    if request is not valid
     * @throws com.musicinfofinder.musicinfofinderservice.exceptions.NoLyricsFoundException if no lyrics are found.
     */
    Optional<SearchLyricsResponse> extractLyrics(SearchLyricsRequest request);
}
