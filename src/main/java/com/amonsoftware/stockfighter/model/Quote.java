package com.amonsoftware.stockfighter.model;

import lombok.Data;

@Data
public class Quote {
    private boolean ok;
    private String symbol;
    private String venue;
    /**
     * best price currently bid for the stock (buy price)
     */
    private Integer bid;
    /**
     * best price currently offered for the stock (sell price)
     */
    private Integer ask;
    /**
     * aggregate size of all orders at the best bid
     */
    private Integer bidSize;
    /**
     * aggregate size of all orders at the best ask
     */
    private Integer askSize;
    /**
     * aggregate size of *all bids*
     */
    private Integer bidDepth;
    /**
     * aggregate size of *all asks*
     */
    private Integer askDepth;
    /**
     * price of last trade
     */
    private Integer last;
    /**
     * quantity of last trade
     */
    private Integer lastSize;
    /**
     * timestamp of last trade
     */
    private String lastTrade;
    /**
     * ts we last updated quote at (server-side)
     */
    private String quoteTime;

}
