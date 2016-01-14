package com.amonsoftware.stockfighter.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum OrderDirection {
    @JsonProperty("buy")
    BUY("buy"),
    @JsonProperty("sell")
    SELL("sell");
    private String name;

    OrderDirection(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
