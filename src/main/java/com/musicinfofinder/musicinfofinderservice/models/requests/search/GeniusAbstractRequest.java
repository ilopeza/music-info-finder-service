package com.musicinfofinder.musicinfofinderservice.models.requests.search;

import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

import java.net.URI;

public abstract class GeniusAbstractRequest<T> implements IGeniusRequest<T> {
    protected WebClient.Builder webClientBuilder;

    protected abstract T executeRequest();

    protected abstract URI getUri(UriBuilder uriBuilder);

    @Override
    public T execute() {
        return executeRequest();
    }

    public abstract static class GeniusAbstractRequestBuilder<SELF extends IRequestBuilder<SELF, T>, T>
            implements IRequestBuilder<SELF, T> {
        private WebClient.Builder webClientBuilder;

        public SELF withWebClientBuilder(WebClient.Builder webClientBuilder) {
            this.webClientBuilder = webClientBuilder;
            return self();
        }

        public WebClient.Builder getWebClient() {
            return webClientBuilder;
        }

        @Override
        public SELF self() {
            return (SELF) this;
        }
    }
}
