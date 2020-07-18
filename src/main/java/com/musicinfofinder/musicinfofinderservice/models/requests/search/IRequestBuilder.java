package com.musicinfofinder.musicinfofinderservice.models.requests.search;

/**
 * Generic request builder interface.
 *
 * @param <S> The builder implementation itself
 * @param <T> The request to build
 */
public interface IRequestBuilder<S, T> {
    T build();

    S self();
}
