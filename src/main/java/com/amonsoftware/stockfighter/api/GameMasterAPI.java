package com.amonsoftware.stockfighter.api;

import com.amonsoftware.stockfighter.model.Instance;
import com.amonsoftware.stockfighter.model.LevelResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class GameMasterAPI {
    private static final String BASE_URL = "https://www.stockfighter.io/gm/";
    private static final Logger log = LoggerFactory.getLogger(GameMasterAPI.class);
    private final Executor executorService = Executors.newFixedThreadPool(10);
    private RestTemplate restTemplate = new RestTemplate();

    public GameMasterAPI(String apiKey) {
        restTemplate.setInterceptors(Collections.<ClientHttpRequestInterceptor>singletonList(new HeaderRequestInterceptor(apiKey)));
    }

    public CompletableFuture<LevelResponse> startLevel(String levelName) {
        return CompletableFuture.supplyAsync(
                () -> restTemplate.postForObject(UriComponentsBuilder
                        .fromHttpUrl(BASE_URL)
                        .path("/levels/{level}")
                        .buildAndExpand(levelName)
                        .toUriString(), null, LevelResponse.class), executorService)
                .whenComplete((levelResponse, throwable) -> log.debug("Start level response: '{}'", levelResponse));
    }

    public CompletableFuture<Instance> getInstanceDetails(Integer instance) {
        return CompletableFuture.supplyAsync(
                () -> restTemplate.getForObject(UriComponentsBuilder
                        .fromHttpUrl(BASE_URL)
                        .path("/instances/{instance}")
                        .buildAndExpand(instance)
                        .toUriString(), Instance.class), executorService)
                .whenComplete((instanceResponse, throwable) -> log.debug("Get instance detail response: '{}'", instanceResponse));
    }
}
