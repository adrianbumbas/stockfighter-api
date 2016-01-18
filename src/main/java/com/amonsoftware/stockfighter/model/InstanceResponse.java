package com.amonsoftware.stockfighter.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class InstanceResponse {
    private boolean ok;
    private boolean done;
    private Integer id;
    private String state;
    @JsonProperty("flash")
    private FlashMessage flashMessage;
    private Details details;
}
