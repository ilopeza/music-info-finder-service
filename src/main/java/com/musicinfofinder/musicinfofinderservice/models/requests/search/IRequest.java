package com.musicinfofinder.musicinfofinderservice.models.requests.search;

/**
 * Generic interface for the request.
 *
 * @param <T> Generic type for the response.
 */
public interface IRequest<T> {
    T execute();
}
