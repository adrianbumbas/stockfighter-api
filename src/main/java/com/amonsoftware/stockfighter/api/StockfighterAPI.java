package com.amonsoftware.stockfighter.api;

import com.amonsoftware.stockfighter.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger log = LoggerFactory.getLogger(StockfighterAPI.class);
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
                        .toUriString(), Heartbeat.class), executorService)
                .whenComplete((heartbeat, throwable) -> log.debug("Heartbeat response: '{}'", heartbeat));

    }

    public CompletableFuture<VenueStatus> getVenueStatus(String venue) {
        return CompletableFuture.supplyAsync(
                () -> restTemplate.getForObject(UriComponentsBuilder
                        .fromHttpUrl(BASE_URL)
                        .path("venues/{venue}/heartbeat")
                        .buildAndExpand(venue)
                        .toUriString(), VenueStatus.class), executorService)
                .whenComplete((venueStatus, throwable) -> log.debug("Venue status response: '{}'", venueStatus));
    }

    public CompletableFuture<StocksOnAVenue> getStocksOnAVenue(String venue) {
        return CompletableFuture.supplyAsync(
                () -> restTemplate.getForObject(UriComponentsBuilder
                        .fromHttpUrl(BASE_URL)
                        .path("/venues/{venue}/stocks")
                        .buildAndExpand(venue)
                        .toUriString(), StocksOnAVenue.class), executorService)
                .whenComplete((stocksOnAVenue, throwable) -> log.debug("Stocks on a venue response: '{}'", stocksOnAVenue));
    }

    public CompletableFuture<Orderbook> getOrderbookForStock(String venue, String stock) {
        return CompletableFuture.supplyAsync(
                () -> restTemplate.getForObject(UriComponentsBuilder
                        .fromHttpUrl(BASE_URL)
                        .path("/venues/{venue}/stocks/{stock}")
                        .buildAndExpand(venue, stock)
                        .toUriString(), Orderbook.class), executorService)
                .whenComplete((orderbook, throwable) -> log.debug("Orderbook for stock response: '{}'", orderbook));
    }

    public CompletableFuture<NewOrderResponse> placeNewOrder(NewOrderRequest orderRequest) {
        return CompletableFuture.supplyAsync(
                () -> restTemplate.postForObject(UriComponentsBuilder
                        .fromHttpUrl(BASE_URL)
                        .path("/venues/{venue}/stocks/{stock}/orders")
                        .buildAndExpand(orderRequest.getVenue(), orderRequest.getStock())
                        .toUriString(), orderRequest, NewOrderResponse.class), executorService)
                .whenComplete((orderResponse, throwable) -> log.debug("Place new order response: '{}'", orderResponse));
    }

    public CompletableFuture<Quote> getQuoteForStock(String venue, String stock) {
        return CompletableFuture.supplyAsync(
                () -> restTemplate.getForObject(UriComponentsBuilder
                        .fromHttpUrl(BASE_URL)
                        .path("/venues/{venue}/stocks/{stock}/quote")
                        .buildAndExpand(venue, stock)
                        .toUriString(), Quote.class), executorService)
                .whenComplete((quote, throwable) -> log.debug("Get quote for stock response: '{}'", quote));
    }

    public CompletableFuture<OrderStatusResponse> getOrderStatus(Integer orderId, String venue, String stock) {
        return CompletableFuture.supplyAsync(
                () -> restTemplate.getForObject(UriComponentsBuilder
                        .fromHttpUrl(BASE_URL)
                        .path("/venues/{venue}/stocks/{stock}/orders/{orderId}")
                        .buildAndExpand(venue, stock, orderId)
                        .toUriString(), OrderStatusResponse.class), executorService)
                .whenComplete((orderStatusResponse, throwable) -> log.debug("Get order status response: '{}'", orderStatusResponse));
    }

    public CompletableFuture<OrderStatusResponse> cancelOrder(Integer orderId, String venue, String stock) {
        return CompletableFuture.supplyAsync(
                () -> restTemplate.exchange(UriComponentsBuilder
                        .fromHttpUrl(BASE_URL)
                        .path("/venues/{venue}/stocks/{stock}/orders/{orderId}")
                        .buildAndExpand(venue, stock, orderId)
                        .toUriString(), HttpMethod.DELETE, null, OrderStatusResponse.class).getBody(), executorService)
                .whenComplete((orderStatusResponse, throwable) -> log.debug("Cancel order response: '{}'", orderStatusResponse));
    }
}
