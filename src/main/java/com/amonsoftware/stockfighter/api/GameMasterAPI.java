package com.amonsoftware.stockfighter.api;

import com.amonsoftware.stockfighter.model.InstanceResponse;
import com.amonsoftware.stockfighter.model.LevelResponse;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.concurrent.CompletableFuture;

public class GameMasterAPI {
    private static final String BASE_URL = "https://www.stockfighter.io/gm/";

    private RestTemplate restTemplate = new RestTemplate();

    public GameMasterAPI(String apiKey) {
        restTemplate.setInterceptors(Collections.<ClientHttpRequestInterceptor>singletonList(new HeaderRequestInterceptor(apiKey)));
    }

    public CompletableFuture<LevelResponse> startLevel(String levelName) {
        return CompletableFuture.supplyAsync(
                () -> restTemplate.postForObject(UriComponentsBuilder
                        .fromHttpUrl(BASE_URL)
                        .path("/levels/{level}")
                        .buildAndExpand(levelName).toUriString(), null, LevelResponse.class)
        );
    }

    public CompletableFuture<InstanceResponse> getInstanceDetails(Integer instance) {
        return CompletableFuture.supplyAsync(
                () -> restTemplate.getForObject(UriComponentsBuilder
                        .fromHttpUrl(BASE_URL)
                        .path("/instances/{instance}")
                        .buildAndExpand(instance).toUriString(), InstanceResponse.class)
        );
    }
}
