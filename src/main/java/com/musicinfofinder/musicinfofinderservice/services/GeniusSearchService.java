package com.musicinfofinder.musicinfofinderservice.services;

import com.musicinfofinder.musicinfofinderservice.exceptions.BadRequestException;
import com.musicinfofinder.musicinfofinderservice.exceptions.ClientException;
import com.musicinfofinder.musicinfofinderservice.exceptions.NoLyricsFoundException;
import com.musicinfofinder.musicinfofinderservice.models.requests.search.GeniusSearchLyricsRequest;
import com.musicinfofinder.musicinfofinderservice.models.requests.search.SearchLyricsRequest;
import com.musicinfofinder.musicinfofinderservice.models.response.BaseGeniusResponse;
import com.musicinfofinder.musicinfofinderservice.models.response.search.genius.GeniusResult;
import com.musicinfofinder.musicinfofinderservice.models.response.search.genius.Hit;
import com.musicinfofinder.musicinfofinderservice.models.response.search.genius.HitType;
import com.musicinfofinder.musicinfofinderservice.models.response.search.genius.SearchResponse;
import lombok.extern.slf4j.Slf4j;
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

/**
 * Genius implementation to search lyrics.
 */
@Slf4j
@Service
public class GeniusSearchService implements SearchService {

    @Autowired
    private WebClient.Builder webClientBuilder;
    @Autowired
    private ExtractionLyricsService extractionLyricsService;

    @Value("${genius.access.token:Config server issue}")
    private String token;

    @Override
    public BaseGeniusResponse getExternalSearchRawResponse(SearchLyricsRequest request) {
        return searchInGenius(request);
    }

    @Override
    public Optional<GeniusResult> filterResult(SearchLyricsRequest request) {
        BaseGeniusResponse geniusResponse = searchInGenius(request);
        if (isNull(geniusResponse)) {
            return empty();
        }
        return findResultInGeniusResponse(request, geniusResponse);
    }

    @Override
    public Optional<String> getLyrics(SearchLyricsRequest request) {
        log.info("Searching for lyrics with request {}", request);
        Optional<GeniusResult> result = filterResult(request);
        if (result.isEmpty()) {
            log.error("No result could be retrieved from external client response for request {}", request);
            throw new NoLyricsFoundException("No result could be retrieved from external client response");
        }
        String lyrics = extractionLyricsService.extractLyrics(result.get());
        log.info("Lyrics found for request {}", request);

        return Optional.of(lyrics);
    }

    private BaseGeniusResponse searchInGenius(SearchLyricsRequest request) {
        if (isBlank(request.getAlbumName())
                && isBlank(request.getArtistName())
                && isBlank(request.getTrackName())) {
            log.error("Artist name and track name can not be blank {}", request);
            throw new BadRequestException("Artist name and track name can not be blank");
        }
        GeniusSearchLyricsRequest geniusSearchLyricsRequest = GeniusSearchLyricsRequest.builder()
                .withQuery(request)
                .withAccessToken(token)
                .withWebClientBuilder(webClientBuilder)
                .build();

        return geniusSearchLyricsRequest.execute();
    }

    private Optional<GeniusResult> findResultInGeniusResponse(SearchLyricsRequest request, BaseGeniusResponse response) {
        if (isBlank(request.getArtistName())
                || isBlank(request.getTrackName())
                || isBlank(request.getAlbumName())) {
            log.error("Filters can not be empty {}", request);
            throw new BadRequestException("Filters can not be empty");
        }

        if (isNull(response)) {
            log.error("Response from client is null");
            throw new ClientException("Response from client is null");
        }

        SearchResponse searchResponse = response.getResponse();
        List<Hit> hits = searchResponse.getHits();
        if (CollectionUtils.isEmpty(hits)) {
            log.error("No hits were found in response from client. {}", response);
            throw new NoLyricsFoundException("No hits were found in response from client.");
        }
        return hits.stream()
                .filter(hit -> !isNull(hit.getGeniusResult())
                        && HitType.SONG.equals(hit.getType()))
                .map(Hit::getGeniusResult)
                .filter(geniusResult -> equalsIgnoreCase(request.getTrackName(), geniusResult.getTitle()) ||
                        (isNotBlank(geniusResult.getFullTitle())
                                && geniusResult.getFullTitle().contains(request.getTrackName())))
                .filter(geniusResult -> !isNull(geniusResult.getPrimaryArtist())
                        && isNotBlank(geniusResult.getPrimaryArtist().getName())
                        && equalsIgnoreCase(geniusResult.getPrimaryArtist().getName(), request.getArtistName()))
                .findFirst();
    }
}
