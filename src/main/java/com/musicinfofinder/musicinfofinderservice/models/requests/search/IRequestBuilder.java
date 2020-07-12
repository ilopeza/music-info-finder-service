package com.musicinfofinder.musicinfofinderservice.models.requests.search;

public interface IRequestBuilder<S, T> {

    T build();

    S self();
}
