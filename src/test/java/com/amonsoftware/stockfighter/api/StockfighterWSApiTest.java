package com.amonsoftware.stockfighter.api;

import com.amonsoftware.stockfighter.api.tools.WebSocketTestServer;
import com.amonsoftware.stockfighter.model.QuoteSubscription;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class StockfighterWSApiTest {
    private static final int NUMBER_OF_MESSAGES = 20;
    WebSocketTestServer webSocketTestServer = new WebSocketTestServer();
    StockfighterWSApi stockfighterWSApi = new StockfighterWSApi("ws://localhost:8025/");
    CountDownLatch countDownLatch = new CountDownLatch(NUMBER_OF_MESSAGES);
    List<QuoteSubscription> quoteSubscriptions = new ArrayList<>();

    @Test
    public void testGetQuoteSubscription() throws Exception {
        webSocketTestServer.runTickerTapeTestServer();
        stockfighterWSApi.getQuoteSubscription("RWOMEX", "MS44144592").subscribe(quoteSubscription -> {
            quoteSubscriptions.add(quoteSubscription);
            countDownLatch.countDown();
        });
        countDownLatch.await(1, TimeUnit.SECONDS);
        assertEquals(NUMBER_OF_MESSAGES, quoteSubscriptions.size());
        assertEquals("IGLO", quoteSubscriptions.get(0).getQuote().getSymbol());
        assertEquals(4948, quoteSubscriptions.get(0).getQuote().getAsk().longValue());
        assertEquals(11353, quoteSubscriptions.get(2).getQuote().getAskSize().longValue());
        webSocketTestServer.stopServer();
    }
}