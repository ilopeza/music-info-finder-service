package com.musicinfofinder.musicinfofinderservice.models.requests.search;

import com.musicinfofinder.musicinfofinderservice.exceptions.BadRequestException;
import com.musicinfofinder.musicinfofinderservice.exceptions.ClientException;
import com.musicinfofinder.musicinfofinderservice.models.response.BaseGeniusResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotBlank;
import java.net.URI;
import java.nio.charset.StandardCharsets;

import static com.musicinfofinder.musicinfofinderservice.utils.Constants.ACCESS_TOKEN_KEY;
import static com.musicinfofinder.musicinfofinderservice.utils.Constants.SEARCH_PATH;
import static com.musicinfofinder.musicinfofinderservice.utils.Constants.SEARCH_QUERY_KEY;

/**
 * Search request object to get lyrics from external Genius service.
 * See https://docs.genius.com/#search-h2
 * The Genius search API looks like this: api.genius.com/search?q=Kendrick%20Lamar
 * To  call the api, the acces token should always be present.
 */
public class GeniusSearchLyricsRequest extends GeniusAbstractRequest<BaseGeniusResponse> {
    @NotBlank
    private String query;
    @NotBlank
    private String accessToken;

    public static GeniusSearchLyricsRequestBuilder builder() {
        return new GeniusSearchLyricsRequestBuilder();
    }

    @Override
    protected BaseGeniusResponse executeRequest() {
        Mono<BaseGeniusResponse> lyricsResponseMono = webClientBuilder
                .build()
                .get()
                .uri(this::getUri)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML)
                .acceptCharset(StandardCharsets.UTF_8)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse -> Mono.error(new BadRequestException(String.valueOf(clientResponse.rawStatusCode()))))
                .onStatus(HttpStatus::is2xxSuccessful, clientResponse -> Mono.error(new ClientException(String.valueOf(clientResponse.rawStatusCode()))))
                .bodyToMono(BaseGeniusResponse.class);

        return lyricsResponseMono.block();
    }

    @Override
    protected URI getUri(UriBuilder uriBuilder) {
        return uriBuilder
                .path(SEARCH_PATH)
                .queryParam(SEARCH_QUERY_KEY, query)
                .queryParam(ACCESS_TOKEN_KEY, accessToken)
                .build();
    }

    public static class GeniusSearchLyricsRequestBuilder extends GeniusAbstractRequestBuilder<GeniusSearchLyricsRequestBuilder, GeniusSearchLyricsRequest> {
        private SearchLyricsRequest searchLyricsRequest;
        private String accessToken;

        @Override
        public GeniusSearchLyricsRequest build() {
            String query = buildQuery(searchLyricsRequest);
            GeniusSearchLyricsRequest request = new GeniusSearchLyricsRequest();
            request.query = query;
            request.accessToken = accessToken;
            request.webClientBuilder = getWebClient();
            return request;
        }

        public GeniusSearchLyricsRequestBuilder withAccessToken(String accessToken) {
            this.accessToken = accessToken;
            return this;
        }

        public GeniusSearchLyricsRequestBuilder withQuery(SearchLyricsRequest searchLyricsRequest) {
            this.searchLyricsRequest = searchLyricsRequest;
            return this;
        }

        /**
         * Creates the search query with the correct format.
         *
         * @param searchLyricsRequest specifies the artist name, the track and the album. Currently, genius only uses track and artist.
         * @return formatted query
         */
        private String buildQuery(SearchLyricsRequest searchLyricsRequest) {
            return new StringBuilder()
                    .append(searchLyricsRequest.getArtistName().toLowerCase())
                    .append(" ")
                    .append(searchLyricsRequest.getTrackName().toLowerCase())
                    .toString();
        }
    }
}
