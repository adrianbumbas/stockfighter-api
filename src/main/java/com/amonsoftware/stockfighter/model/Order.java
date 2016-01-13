package com.amonsoftware.stockfighter.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Order {
    private Long price;
    @JsonProperty("qty")
    private Long quantity;
    @JsonProperty("isBuy")
    private boolean buy;
}
