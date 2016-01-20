package com.amonsoftware.stockfighter.api;

import com.amonsoftware.stockfighter.model.*;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class StockfighterAPI {
    private static final String BASE_URL = "https://api.stockfighter.io/ob/api/";
    private final Executor executorService = Executors.newFixedThreadPool(10);

    private RestTemplate restTemplate = new RestTemplate();

    public StockfighterAPI(String apiKey) {
        restTemplate.setInterceptors(Collections.<ClientHttpRequestInterceptor>singletonList(new HeaderRequestInterceptor(apiKey)));
    }

    public CompletableFuture<Heartbeat> getHeartbeat() {
        return CompletableFuture.supplyAsync(
                () -> restTemplate.getForObject(UriComponentsBuilder
                        .fromHttpUrl(BASE_URL)
                        .path("heartbeat")
                        .toUriString(), Heartbeat.class), executorService);

    }

    public CompletableFuture<VenueStatus> getVenueStatus(String venue) {
        return CompletableFuture.supplyAsync(
                () -> restTemplate.getForObject(UriComponentsBuilder
                        .fromHttpUrl(BASE_URL)
                        .path("venues/{venue}/heartbeat")
                        .buildAndExpand(venue)
                        .toUriString(), VenueStatus.class), executorService);
    }

    public CompletableFuture<StocksOnAVenue> getStocksOnAVenue(String venue) {
        return CompletableFuture.supplyAsync(
                () -> restTemplate.getForObject(UriComponentsBuilder
                        .fromHttpUrl(BASE_URL)
                        .path("/venues/{venue}/stocks")
                        .buildAndExpand(venue)
                        .toUriString(), StocksOnAVenue.class), executorService);
    }

    public CompletableFuture<Orderbook> getOrderbookForStock(String venue, String stock) {
        return CompletableFuture.supplyAsync(
                () -> restTemplate.getForObject(UriComponentsBuilder
                        .fromHttpUrl(BASE_URL)
                        .path("/venues/{venue}/stocks/{stock}")
                        .buildAndExpand(venue, stock)
                        .toUriString(), Orderbook.class), executorService);
    }

    public CompletableFuture<NewOrderResponse> placeNewOrder(NewOrderRequest orderRequest) {
        return CompletableFuture.supplyAsync(
                () -> restTemplate.postForObject(UriComponentsBuilder
                        .fromHttpUrl(BASE_URL)
                        .path("/venues/{venue}/stocks/{stock}/orders")
                        .buildAndExpand(orderRequest.getVenue(), orderRequest.getStock())
                        .toUriString(), orderRequest, NewOrderResponse.class), executorService);
    }

    public CompletableFuture<Quote> getQuoteForStock(String venue, String stock) {
        return CompletableFuture.supplyAsync(
                () -> restTemplate.getForObject(UriComponentsBuilder
                        .fromHttpUrl(BASE_URL)
                        .path("/venues/{venue}/stocks/{stock}/quote")
                        .buildAndExpand(venue, stock)
                        .toUriString(), Quote.class), executorService);
    }

    public CompletableFuture<OrderStatusResponse> getOrderStatus(Integer orderId, String venue, String stock) {
        return CompletableFuture.supplyAsync(
                () -> restTemplate.getForObject(UriComponentsBuilder
                        .fromHttpUrl(BASE_URL)
                        .path("/venues/{venue}/stocks/{stock}/orders/{orderId}")
                        .buildAndExpand(venue, stock, orderId)
                        .toUriString(), OrderStatusResponse.class), executorService);
    }

    public CompletableFuture<OrderStatusResponse> cancelOrder(Integer orderId, String venue, String stock) {
        return CompletableFuture.supplyAsync(
                () -> restTemplate.exchange(UriComponentsBuilder
                        .fromHttpUrl(BASE_URL)
                        .path("/venues/{venue}/stocks/{stock}/orders/{orderId}")
                        .buildAndExpand(venue, stock, orderId)
                        .toUriString(), HttpMethod.DELETE, null, OrderStatusResponse.class).getBody(), executorService);
    }
}
