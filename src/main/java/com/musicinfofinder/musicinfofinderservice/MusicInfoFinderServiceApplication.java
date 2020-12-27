package com.musicinfofinder.musicinfofinderservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

import static com.musicinfofinder.musicinfofinderservice.utils.Constants.BASE_URL;

@EnableEurekaClient
@SpringBootApplication
public class MusicInfoFinderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MusicInfoFinderServiceApplication.class, args);
    }

    @Bean
    public WebClient.Builder getWebClientBuilder() {
        return WebClient.builder()
                .baseUrl(BASE_URL);
    }
}
