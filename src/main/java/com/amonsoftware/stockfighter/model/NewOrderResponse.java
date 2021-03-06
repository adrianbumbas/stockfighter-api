package com.amonsoftware.stockfighter.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class NewOrderResponse {
    private boolean ok;
    private String symbol;
    private String venue;
    private OrderDirection direction;
    @JsonProperty("originalQty")
    private Integer originalQuantity;
    @JsonProperty("qty")
    private Integer outstandingQuantity;
    private Integer price;
    private OrderType type;
    private String account;
    private Integer id;
    @JsonProperty("ts")
    private String timestamp;
    private List<OrderFill> fills;
    private Integer totalFilled;
    private boolean open;
    private String error;
}
