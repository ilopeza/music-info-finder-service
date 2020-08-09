package com.musicinfofinder.musicinfofinderservice.controllers;

import com.musicinfofinder.musicinfofinderservice.models.requests.search.SearchLyricsRequest;
import com.musicinfofinder.musicinfofinderservice.models.response.BaseGeniusResponse;
import com.musicinfofinder.musicinfofinderservice.models.response.InfoFinderResponse;
import com.musicinfofinder.musicinfofinderservice.models.response.search.genius.GeniusResult;
import com.musicinfofinder.musicinfofinderservice.services.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/search")
public class GeniusSearchController {

    @Autowired
    SearchService searchService;

    @GetMapping("/lyrics")
    public BaseGeniusResponse findLyrics(@Valid SearchLyricsRequest request) {
        return searchService.getExternalSearchRawResponse(request);
    }

    @GetMapping("/lyrics/result")
    public Optional<GeniusResult> findResult(@Valid SearchLyricsRequest request) {
        return searchService.filterResult(request);
    }

    @GetMapping("/lyrics/extract")
    public InfoFinderResponse extractLyrics(@Valid SearchLyricsRequest request) {
        log.info("Starting search for request: {}", request);
        Optional<String> extractedLyrics = searchService.getLyrics(request);
        InfoFinderResponse response = InfoFinderResponse.builder()
                .status(extractedLyrics.isPresent() ? 2000 : 3000)
                .lyrics(extractedLyrics.orElse("No lyrics found"))
                .build();
        log.info("Returning response: {}", response);
        return response;
    }
}
