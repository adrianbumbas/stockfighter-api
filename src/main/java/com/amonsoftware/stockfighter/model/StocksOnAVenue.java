package com.amonsoftware.stockfighter.model;

import lombok.Data;

import java.util.List;

@Data
public class StocksOnAVenue {
    private boolean ok;
    private List<Symbol> symbols;
}
