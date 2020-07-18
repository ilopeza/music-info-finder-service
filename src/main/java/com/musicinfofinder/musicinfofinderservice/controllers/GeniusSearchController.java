package com.musicinfofinder.musicinfofinderservice.controllers;

import com.musicinfofinder.musicinfofinderservice.models.requests.search.SearchLyricsRequest;
import com.musicinfofinder.musicinfofinderservice.models.response.BaseGeniusResponse;
import com.musicinfofinder.musicinfofinderservice.models.response.search.Result;
import com.musicinfofinder.musicinfofinderservice.services.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/search")
public class GeniusSearchController {

    @Autowired
    SearchService searchService;

    @GetMapping("/lyrics")
    public BaseGeniusResponse findLyrics(@Valid SearchLyricsRequest request) {
        return searchService.findLyrics(request);
    }

    @GetMapping("/lyrics/result")
    public Optional<Result> findResult(@Valid SearchLyricsRequest request) {
        return searchService.findResult(request);
    }

    @GetMapping("/lyrics/extract")
    public Optional<String> extractLyrics(@Valid SearchLyricsRequest request) {
        return searchService.extractLyrics(request);
    }
}
