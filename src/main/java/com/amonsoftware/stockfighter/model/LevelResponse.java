package com.amonsoftware.stockfighter.model;

import lombok.Data;

import java.util.Map;

@Data
public class LevelResponse {
    private String account;
    private Integer instanceId;
    private Instruction instructions;
    private boolean ok;
    private Integer secondsPerTradingDay;
    private String[] tickers;
    private String[] venues;
    private Map<String, Integer> balances;
}
