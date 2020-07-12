package com.musicinfofinder.musicinfofinderservice.services;

import com.musicinfofinder.musicinfofinderservice.exceptions.BadRequestException;
import com.musicinfofinder.musicinfofinderservice.models.requests.search.GeniusSearchLyricsRequest;
import com.musicinfofinder.musicinfofinderservice.models.requests.search.SearchLyricsRequest;
import com.musicinfofinder.musicinfofinderservice.models.response.BaseGeniusResponse;
import com.musicinfofinder.musicinfofinderservice.models.response.search.Hit;
import com.musicinfofinder.musicinfofinderservice.models.response.search.HitType;
import com.musicinfofinder.musicinfofinderservice.models.response.search.Result;
import com.musicinfofinder.musicinfofinderservice.models.response.search.SearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;
import static java.util.Optional.empty;
import static org.apache.commons.lang.StringUtils.equalsIgnoreCase;
import static org.apache.commons.lang.StringUtils.isBlank;
import static org.apache.commons.lang.StringUtils.isNotBlank;

@Service
public class GeniusSearchService implements SearchService {

    @Autowired
    private WebClient.Builder webClientBuilder;
    @Value("${genius.acccess.token}")
    private String token;

    @Override
    public BaseGeniusResponse findLyrics(SearchLyricsRequest request) {
        return searchInGenius(request);
    }

    @Override
    public Optional<Result> findResult(SearchLyricsRequest request) {
        BaseGeniusResponse geniusResponse = searchInGenius(request);
        if (isNull(geniusResponse)) {
            return empty();
        }
        Optional<Result> resultInGeniusResponse = findResultInGeniusResponse(request, geniusResponse);
        return resultInGeniusResponse;
    }

    private BaseGeniusResponse searchInGenius(SearchLyricsRequest request) {
        if (isBlank(request.getAlbumName())
                && isBlank(request.getArtistName())
                && isBlank(request.getTrackName())) {
            //bad request exception?
            throw new BadRequestException("Artist name and track name can not be blank");
        }
        GeniusSearchLyricsRequest geniusSearchLyricsRequest = GeniusSearchLyricsRequest.builder()
                .withQuery(request)
                .withAccessToken(token)
                .withWebClientBuilder(webClientBuilder)
                .build();

        return geniusSearchLyricsRequest.execute();
    }


    private Optional<Result> findResultInGeniusResponse(SearchLyricsRequest request, BaseGeniusResponse response) {
        if (isBlank(request.getArtistName())
                || isBlank(request.getTrackName())
                || isBlank(request.getAlbumName())) {
            throw new BadRequestException("Filters can not be empty");
        }

        if (isNull(response)) {
            return empty();
        }
        SearchResponse searchResponse = response.getResponse();
        List<Hit> hits = searchResponse.getHits();
        if (CollectionUtils.isEmpty(hits)) {
            return empty();
        }
        Optional<Result> optionalResult = hits.stream().filter(hit -> !isNull(hit.getResult()) && HitType.SONG.equals(hit.getType()))
                .map(Hit::getResult)
                .filter(result -> equalsIgnoreCase(request.getTrackName(), result.getTitle()) ||
                        (isNotBlank(result.getFullTitle())
                                && result.getFullTitle().contains(request.getTrackName())))
                .filter(result -> !isNull(result.getPrimaryArtist())
                        && isNotBlank(result.getPrimaryArtist().getName())
                        && equalsIgnoreCase(result.getPrimaryArtist().getName(), request.getArtistName()))
                .findFirst();
        return optionalResult;
    }

}
