package com.amonsoftware.stockfighter.model;

import lombok.Data;

@Data
public class InstanceResponse {
    private boolean ok;
    private boolean done;
    private Integer id;
    private String state;
    private FlashMessage flashMessage;
    private Details details;
}
