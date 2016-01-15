package com.amonsoftware.stockfighter.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Instruction {
    @JsonProperty("Instructions")
    private String instructions;
    @JsonProperty("Order Types")
    private String orderTypes;
}
