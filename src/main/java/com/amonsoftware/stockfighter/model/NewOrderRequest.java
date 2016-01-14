package com.amonsoftware.stockfighter.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class NewOrderRequest {
    private String account;
    private String venue;
    private String stock;
    private Integer price;
    @JsonProperty("qty")
    private Integer quantity;
    private OrderDirection direction;
    private OrderType orderType;
}
