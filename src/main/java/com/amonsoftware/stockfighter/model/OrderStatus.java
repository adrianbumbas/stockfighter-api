package com.amonsoftware.stockfighter.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class OrderStatus {
    private boolean ok;
    private String symbol;
    private String venue;
    private OrderDirection direction;
    @JsonProperty("originalQty")
    private Integer originalQuantity;
    @JsonProperty("qty")
    private Integer price;
    @JsonProperty("orderType")
    private OrderType type;
    private Integer id;
    private String account;
    @JsonProperty("ts")
    private String timestamp;
    private List<OrderFill> fills;
    private Integer totalFilled;
    private boolean open;
    private String error;
}
