package com.amonsoftware.stockfighter.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum OrderType {
    @JsonProperty("limit")
    LIMIT_ORDER("limit"),
    @JsonProperty("market")
    MARKET_ORDER("market"),
    @JsonProperty("fill-or-kill")
    FILL_OR_KILL("fill-or-kill"),
    @JsonProperty("immediate-or-cancel")
    IMMEDIATE_OR_CANCEL("immediate-or-cancel");
    private String name;

    OrderType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
