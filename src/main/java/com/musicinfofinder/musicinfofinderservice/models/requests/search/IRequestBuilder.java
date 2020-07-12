package com.musicinfofinder.musicinfofinderservice.models.requests.search;

public interface IRequestBuilder<SELF, T> {

    T build();

    SELF self();
}
