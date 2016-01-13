package com.amonsoftware.stockfighter.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Orderbook {
    private boolean ok;
    private String venue;
    private String symbol;
    private List<Bid> bids;
    private List<Ask> asks;
    @JsonProperty("ts")
    private String timestamp;
}
