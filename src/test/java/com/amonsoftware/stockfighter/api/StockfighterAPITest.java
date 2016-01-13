package com.amonsoftware.stockfighter.api;

import com.amonsoftware.stockfighter.model.Orderbook;
import com.amonsoftware.stockfighter.model.StocksOnAVenue;
import com.amonsoftware.stockfighter.model.Symbol;
import com.amonsoftware.stockfighter.model.VenueStatus;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class StockfighterAPITest {
    private StockfighterAPI stockfighterAPI;

    @Before
    public void setUp() throws Exception {
        stockfighterAPI = new StockfighterAPI();

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetHeartbeat() throws Exception {
        assertTrue(stockfighterAPI.getHeartbeat().get().isOk());

    }


    @Test
    public void testGetVenueStatus() throws Exception {
        VenueStatus venueStatus = stockfighterAPI.getVenueStatus("TESTEX").get();
        assertNotNull(venueStatus);
        assertTrue(venueStatus.isOk());
        assertThat(venueStatus.getVenue(), is("TESTEX"));
    }

    @Test
    public void testGetStocksOnAVenue() throws Exception {
        StocksOnAVenue stocksOnAVenue = stockfighterAPI.getStocksOnAVenue("TESTEX").get();
        assertNotNull(stocksOnAVenue);
        assertThat(stocksOnAVenue.getSymbols(),is(Collections.singletonList(new Symbol("Foreign Owned Occluded Bridge Architecture Resources","FOOBAR"))));

    }

    @Test
    public void testGetOrderbookForStock() throws Exception {
        Orderbook orderbook = stockfighterAPI.getOrderbookForStock("TESTEX", "FOOBAR").get();
        assertNotNull(orderbook);
        assertTrue(orderbook.isOk());
        assertThat(orderbook.getVenue(), is("TESTEX"));
        assertThat(orderbook.getSymbol(), is("FOOBAR"));
        assertNotNull(orderbook.getTimestamp());
    }

}