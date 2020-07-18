package com.musicinfofinder.musicinfofinderservice.services;

import com.musicinfofinder.musicinfofinderservice.exceptions.BadRequestException;
import com.musicinfofinder.musicinfofinderservice.exceptions.NoLyricsFoundException;
import com.musicinfofinder.musicinfofinderservice.exceptions.ValidationException;
import com.musicinfofinder.musicinfofinderservice.models.requests.search.GeniusSearchLyricsRequest;
import com.musicinfofinder.musicinfofinderservice.models.requests.search.SearchLyricsRequest;
import com.musicinfofinder.musicinfofinderservice.models.response.BaseGeniusResponse;
import com.musicinfofinder.musicinfofinderservice.models.response.search.Hit;
import com.musicinfofinder.musicinfofinderservice.models.response.search.HitType;
import com.musicinfofinder.musicinfofinderservice.models.response.search.Result;
import com.musicinfofinder.musicinfofinderservice.models.response.search.SearchLyricsResponse;
import com.musicinfofinder.musicinfofinderservice.models.response.search.SearchResponse;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;
import static java.util.Optional.empty;
import static org.apache.commons.lang.StringUtils.equalsIgnoreCase;
import static org.apache.commons.lang.StringUtils.isBlank;
import static org.apache.commons.lang.StringUtils.isEmpty;
import static org.apache.commons.lang.StringUtils.isNotBlank;

/**
 * Genius implementation to search lyrics.
 */
@Slf4j
@Service
public class GeniusSearchService implements SearchService {

    @Autowired
    private WebClient.Builder webClientBuilder;
    @Value("${genius.acccess.token}")
    private String token;

    @Override
    public BaseGeniusResponse getExternalSearchRawResponse(SearchLyricsRequest request) {
        return searchInGenius(request);
    }

    @Override
    public Optional<Result> filterResult(SearchLyricsRequest request) {
        BaseGeniusResponse geniusResponse = searchInGenius(request);
        if (isNull(geniusResponse)) {
            return empty();
        }
        return findResultInGeniusResponse(request, geniusResponse);
    }

    @Override
    public Optional<SearchLyricsResponse> extractLyrics(SearchLyricsRequest request) {
        Optional<Result> result = filterResult(request);
        if (result.isEmpty()) {
            return empty();
        }
        String lyrics = extractLyrics(result.get());
        SearchLyricsResponse response = SearchLyricsResponse.builder()
                .lyrics(lyrics)
                .build();
        return Optional.of(response);
    }

    private BaseGeniusResponse searchInGenius(SearchLyricsRequest request) {
        if (isBlank(request.getAlbumName())
                && isBlank(request.getArtistName())
                && isBlank(request.getTrackName())) {
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
        return hits
                .stream()
                .filter(hit -> !isNull(hit.getResult())
                        && HitType.SONG.equals(hit.getType()))
                .map(Hit::getResult)
                .filter(result -> equalsIgnoreCase(request.getTrackName(), result.getTitle()) ||
                        (isNotBlank(result.getFullTitle())
                                && result.getFullTitle().contains(request.getTrackName())))
                .filter(result -> !isNull(result.getPrimaryArtist())
                        && isNotBlank(result.getPrimaryArtist().getName())
                        && equalsIgnoreCase(result.getPrimaryArtist().getName(), request.getArtistName()))
                .findFirst();
    }

    private String extractLyrics(Result result) {
        if (isNull(result)) {
            throw new ValidationException("No lyrics can be extracted from empty Genius response");
        }
        String geniusLyricsUrl = result.getUrl();
        if (isEmpty(geniusLyricsUrl)) {
            throw new NoLyricsFoundException("No url was found");
        }
        Document document;
        try {
            document = Jsoup.connect(geniusLyricsUrl).get();
        } catch (IOException e) {
            throw new NoLyricsFoundException("Lyrics could not be retrieved");
        }
        String lyrics;
        Elements lyricsDiv = document.select(".lyrics");
        if (lyricsDiv.isEmpty()) {
            throw new NoLyricsFoundException("No lyrics found");
        }
        lyrics = Jsoup.clean(lyricsDiv.html(), Whitelist.none().addTags("br"));
        return lyrics.replace("<br>", "").trim();
    }
}
