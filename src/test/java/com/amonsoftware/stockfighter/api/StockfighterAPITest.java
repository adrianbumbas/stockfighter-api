package com.amonsoftware.stockfighter.api;

import com.amonsoftware.stockfighter.model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class StockfighterAPITest {
    private static final String API_KEY = "6c0f27076afa7582a81541544105c0f6b4b055a5";
    private StockfighterAPI stockfighterAPI;

    @Before
    public void setUp() throws Exception {
        stockfighterAPI = new StockfighterAPI(API_KEY);

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
        assertThat(stocksOnAVenue.getSymbols(), is(Collections.singletonList(new Symbol("Foreign Owned Occluded Bridge Architecture Resources", "FOOBAR"))));

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

    @Test
    public void testPlaceNewOrder() throws Exception {
        NewOrderRequest orderRequest = new NewOrderRequest();
        orderRequest.setAccount("EXB123456");
        orderRequest.setVenue("TESTEX");
        orderRequest.setStock("FOOBAR");
        orderRequest.setPrice(1234);
        orderRequest.setQuantity(100);
        orderRequest.setDirection(OrderDirection.BUY);
        orderRequest.setOrderType(OrderType.LIMIT_ORDER);
        NewOrderResponse newOrder = stockfighterAPI.placeNewOrder(orderRequest).get();
        assertNotNull(newOrder);
        assertTrue(newOrder.isOk());
        assertNull(newOrder.getError());
        assertThat(newOrder.getAccount(), is("EXB123456"));
        assertThat(newOrder.getVenue(), is("TESTEX"));
        assertThat(newOrder.getSymbol(), is("FOOBAR"));
        assertThat(newOrder.getDirection(), is(OrderDirection.BUY));
    }

    @Test
    public void testPlaceNewOrder_WrongAccount() throws Exception {
        NewOrderRequest orderRequest = new NewOrderRequest();
        orderRequest.setAccount("dsdsds");
        orderRequest.setVenue("TESTEX");
        orderRequest.setStock("FOOBAR");
        orderRequest.setPrice(1234);
        orderRequest.setQuantity(100);
        orderRequest.setDirection(OrderDirection.BUY);
        orderRequest.setOrderType(OrderType.LIMIT_ORDER);
        NewOrderResponse newOrder = stockfighterAPI.placeNewOrder(orderRequest).get();
        assertNotNull(newOrder);
        assertFalse(newOrder.isOk());
        assertThat(newOrder.getError(), is("Not authorized to trade on that account!"));
    }

    @Test
    public void testGetQuoteForStock() throws Exception {
        Quote quote = stockfighterAPI.getQuoteForStock("TESTEX", "FOOBAR").get();
        assertNotNull(quote);
        assertTrue(quote.isOk());
        assertThat(quote.getVenue(), is("TESTEX"));
        assertThat(quote.getSymbol(), is("FOOBAR"));
    }

    @Test
    public void testGetOrderStatus() throws Exception {
        //place new order
        NewOrderRequest orderRequest = new NewOrderRequest();
        orderRequest.setAccount("EXB123456");
        orderRequest.setVenue("TESTEX");
        orderRequest.setStock("FOOBAR");
        orderRequest.setPrice(1234);
        orderRequest.setQuantity(100);
        orderRequest.setDirection(OrderDirection.BUY);
        orderRequest.setOrderType(OrderType.LIMIT_ORDER);
        NewOrderResponse newOrder = stockfighterAPI.placeNewOrder(orderRequest).get();

        //get status about order
        OrderStatus response = stockfighterAPI.getOrderStatus(newOrder.getId(), newOrder.getVenue(), newOrder.getSymbol()).get();
        assertNotNull(response);
        assertTrue(response.isOk());
        assertThat(response.getVenue(), is("TESTEX"));
        assertThat(response.getSymbol(), is("FOOBAR"));
    }

    @Test
    public void testCancelOrder() throws Exception {
        //place new order
        NewOrderRequest orderRequest = new NewOrderRequest();
        orderRequest.setAccount("EXB123456");
        orderRequest.setVenue("TESTEX");
        orderRequest.setStock("FOOBAR");
        orderRequest.setPrice(1234);
        orderRequest.setQuantity(100);
        orderRequest.setDirection(OrderDirection.BUY);
        orderRequest.setOrderType(OrderType.LIMIT_ORDER);
        NewOrderResponse newOrder = stockfighterAPI.placeNewOrder(orderRequest).get();

        //cancel the order
        OrderStatus response = stockfighterAPI.cancelOrder(newOrder.getId(), newOrder.getVenue(), newOrder.getSymbol()).get();
        assertNotNull(response);
        assertTrue(response.isOk());
        assertThat(response.getVenue(), is("TESTEX"));
        assertThat(response.getSymbol(), is("FOOBAR"));
    }

    @Test
    public void testGetStatusForAllOrders() throws Exception {
        OrdersStatusList ordersStatusList = stockfighterAPI.getStatusForAllOrders("TESTEX", "EXB123456").get();
        assertNotNull(ordersStatusList);
        assertTrue(ordersStatusList.isOk());
        assertThat(ordersStatusList.getVenue(), is("TESTEX"));
    }

    @Test
    public void testGetStatusForAllOrdersInAStock() throws Exception {
        OrdersStatusList ordersStatusList = stockfighterAPI.getStatusForAllOrdersInAStock("TESTEX", "EXB123456", "FOOBAR").get();
        assertNotNull(ordersStatusList);
        assertTrue(ordersStatusList.isOk());
        assertThat(ordersStatusList.getVenue(), is("TESTEX"));
    }
}