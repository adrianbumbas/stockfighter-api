package com.amonsoftware.stockfighter.model;

import lombok.Data;

import java.util.List;

@Data
public class OrdersStatusList {
    private boolean ok;
    private String venue;
    private List<OrderStatus> orders;
}
