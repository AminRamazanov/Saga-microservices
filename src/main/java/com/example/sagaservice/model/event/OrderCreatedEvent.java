package com.example.sagaservice.model.event;

import java.math.BigDecimal;

public class OrderCreatedEvent extends BaseEvent {

    private String orderId;
    private BigDecimal amount;

    public OrderCreatedEvent() {}

    public OrderCreatedEvent(String orderId, BigDecimal amount) {
        this.orderId = orderId;
        this.amount = amount;
        setAggregateId(orderId);
    }

    public String getOrderId() {
        return orderId;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}