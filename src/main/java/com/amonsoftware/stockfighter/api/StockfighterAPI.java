package com.amonsoftware.stockfighter.api;

import com.amonsoftware.stockfighter.model.*;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.concurrent.CompletableFuture;

public class StockfighterAPI {
    private static final String BASE_URL = "https://api.stockfighter.io/ob/api/";

    private RestTemplate restTemplate = new RestTemplate();

    public StockfighterAPI() {
        restTemplate.setInterceptors(Collections.<ClientHttpRequestInterceptor>singletonList(new HeaderRequestInterceptor()));
    }

    public CompletableFuture<Heartbeat> getHeartbeat() {
        return CompletableFuture.supplyAsync(
                () -> restTemplate.getForObject(UriComponentsBuilder
                        .fromHttpUrl(BASE_URL).path("heartbeat").toUriString(), Heartbeat.class));

    }

    public CompletableFuture<VenueStatus> getVenueStatus(String venue) {
        return CompletableFuture.supplyAsync(
                () -> restTemplate.getForObject(UriComponentsBuilder
                        .fromHttpUrl(BASE_URL).path("venues/{venue}/heartbeat").buildAndExpand(venue).toUriString(), VenueStatus.class));
    }

    public CompletableFuture<StocksOnAVenue> getStocksOnAVenue(String venue) {
        return CompletableFuture.supplyAsync(
                () -> restTemplate.getForObject(UriComponentsBuilder
                        .fromHttpUrl(BASE_URL).path("/venues/{venue}/stocks").buildAndExpand(venue).toUriString(), StocksOnAVenue.class));
    }

    public CompletableFuture<Orderbook> getOrderbookForStock(String venue, String stock) {
        return CompletableFuture.supplyAsync(
                () -> restTemplate.getForObject(UriComponentsBuilder
                        .fromHttpUrl(BASE_URL).path("/venues/{venue}/stocks/{stock}").buildAndExpand(venue, stock).toUriString(), Orderbook.class));
    }

    public CompletableFuture<NewOrderResponse> placeNewOrder(NewOrderRequest orderRequest) {
        return CompletableFuture.supplyAsync(
                () -> restTemplate.postForObject(UriComponentsBuilder
                        .fromHttpUrl(BASE_URL)
                        .path("/venues/{venue}/stocks/{stock}/orders")
                        .buildAndExpand(orderRequest.getVenue(), orderRequest.getStock()).toUriString(), orderRequest, NewOrderResponse.class)
        );
    }

    public CompletableFuture<Quote> getQuoteForStock(String venue, String stock) {
        return CompletableFuture.supplyAsync(
                () -> restTemplate.getForObject(UriComponentsBuilder
                        .fromHttpUrl(BASE_URL).path("/venues/{venue}/stocks/{stock}/quote").buildAndExpand(venue, stock).toUriString(), Quote.class));
    }

    public CompletableFuture<OrderStatusResponse> getOrderStatus(Integer orderId, String venue, String stock) {
        return CompletableFuture.supplyAsync(
                () -> restTemplate.getForObject(UriComponentsBuilder
                        .fromHttpUrl(BASE_URL).path("/venues/{venue}/stocks/{stock}/orders/{orderId}").buildAndExpand(venue, stock, orderId).toUriString(), OrderStatusResponse.class));
    }
}
