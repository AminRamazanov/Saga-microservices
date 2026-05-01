package com.example.sagaservice.model.event;

import java.math.BigDecimal;

public class PaymentStartCommand extends BaseEvent {

    private String orderId;
    private BigDecimal amount;

    public PaymentStartCommand() {}

    public PaymentStartCommand(String orderId, BigDecimal amount) {
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