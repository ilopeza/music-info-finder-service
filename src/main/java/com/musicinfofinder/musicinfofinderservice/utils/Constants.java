package com.musicinfofinder.musicinfofinderservice.utils;

public class Constants {
    public static final String HTTPS = "https";
    public static final String API_GENIUS = "api.genius.com";
    public static final String SEARCH_PATH = "search";
    public static final String BASE_URL = HTTPS + "://" + API_GENIUS;
    public static final String AUTH_HEADER_KEY = "Authorization";
    public static final String BEARER_KEY = "Bearer";
    public static final String TOKEN_VALUE = "";
    public static final String AUTH_HEADER_VALUE = BEARER_KEY + " " + TOKEN_VALUE;

    public static final String ACCESS_TOKEN_KEY = "access_token";
    public static final String SEARCH_QUERY_KEY = "q";

}
