package com.amonsoftware.stockfighter.api;

import com.amonsoftware.stockfighter.model.QuoteSubscription;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.glassfish.tyrus.ext.client.java8.SessionBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.UriComponentsBuilder;
import rx.Observable;

import java.io.IOException;
import java.net.URI;

public class StockfighterWSApi {
    public static final String QUOTES_PATH = "{account}/venues/{venue}/tickertape";
    private static final String WS_API_DEFAULT_URL = "wss://api.stockfighter.io/ob/api/ws/";
    private static final Logger log = LoggerFactory.getLogger(StockfighterWSApi.class);
    private ObjectMapper objectMapper = new ObjectMapper();
    private String baseUrl;

    public StockfighterWSApi() {
    }

    public StockfighterWSApi(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public Observable<QuoteSubscription> getQuoteSubscription(String venue, String account) {
        return Observable.create(subscriber -> {

            URI uri = UriComponentsBuilder
                    .fromUriString(baseUrl == null ? WS_API_DEFAULT_URL : baseUrl)
                    .path(QUOTES_PATH)
                    .buildAndExpand(account, venue)
                    .toUri();

            new SessionBuilder().uri(uri)
                    .messageHandler(String.class, (t) -> {
                        if (!subscriber.isUnsubscribed()) {
                            try {
                                QuoteSubscription quoteSubscription = objectMapper.readValue(t, QuoteSubscription.class);
                                if (quoteSubscription.isOk()) {
                                    subscriber.onNext(quoteSubscription);
                                } else {
                                    subscriber.onError(new Throwable(quoteSubscription.getError()));
                                }
                            } catch (IOException e) {
                                log.error("Cannot deserialize object", e);
                            }
                        }
                    })
                    .onError((session, throwable) -> {
                        log.error("Subscriber error", throwable);
                        if (!subscriber.isUnsubscribed()) {
                            subscriber.onError(throwable);
                        }
                    })
                    .connectAsync();
        });
    }
}
