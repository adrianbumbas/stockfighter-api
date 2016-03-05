package com.amonsoftware.stockfighter.model;

import lombok.Data;

@Data
public class QuoteSubscription {

    private Quote quote;
    private boolean ok;
    private String error;

}
