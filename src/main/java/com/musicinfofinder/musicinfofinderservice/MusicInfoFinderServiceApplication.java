package com.musicinfofinder.musicinfofinderservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import static com.musicinfofinder.musicinfofinderservice.utils.Constants.BASE_URL;

@SpringBootApplication
public class MusicInfoFinderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MusicInfoFinderServiceApplication.class, args);
    }

    @Bean
    public RestTemplate getRestTemplate() {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectTimeout(3000);
        return new RestTemplate(factory);
    }

    @Bean
    public WebClient.Builder getWebClientBuilder() {
        return WebClient.builder()
                .baseUrl(BASE_URL);
    }
}
