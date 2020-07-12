package com.musicinfofinder.musicinfofinderservice.services;

import com.musicinfofinder.musicinfofinderservice.exceptions.BadRequestException;
import com.musicinfofinder.musicinfofinderservice.models.requests.search.GeniusSearchLyricsRequest;
import com.musicinfofinder.musicinfofinderservice.models.requests.search.SearchLyricsRequest;
import com.musicinfofinder.musicinfofinderservice.models.response.BaseGeniusResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class GeniusSearchService implements SearchService {

    @Autowired
    private WebClient.Builder webClientBuilder;
    @Value("${genius.acccess.token}")
    private String token;

    @Override
    public BaseGeniusResponse findLyrics(SearchLyricsRequest request) {
        if (StringUtils.isBlank(request.getAlbumName())
                && StringUtils.isBlank(request.getArtistName())
                && StringUtils.isBlank(request.getTrackName())) {
            //bad request exception?
            throw new BadRequestException("Artist name and track name can not be blank");
        }
        GeniusSearchLyricsRequest geniusSearchLyricsRequest = GeniusSearchLyricsRequest.builder()
                .withQuery(request)
                .withAccessToken(token)
                .withWebClientBuilder(webClientBuilder)
                .build();

        BaseGeniusResponse response = geniusSearchLyricsRequest.execute();

        return response;
    }
}
