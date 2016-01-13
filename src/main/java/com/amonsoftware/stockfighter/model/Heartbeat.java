package com.amonsoftware.stockfighter.model;

import lombok.Data;

@Data
public class Heartbeat {
    private boolean ok;
    private String error;
}
