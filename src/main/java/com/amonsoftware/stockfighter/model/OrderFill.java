package com.amonsoftware.stockfighter.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OrderFill {
    private Integer price;
    @JsonProperty("qty")
    private Integer quantity;
    @JsonProperty("ts")
    private String timestamp;
}
