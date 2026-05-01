package com.example.sagaservice.model.event;

public class OrderCancelledEvent extends BaseEvent {

    private String orderId;
    private String reason;

    public OrderCancelledEvent() {}

    public OrderCancelledEvent(String orderId, String reason) {
        this.orderId = orderId;
        this.reason = reason;
        setAggregateId(orderId);
    }

    public String getOrderId() {
        return orderId;
    }

    public String getReason() {
        return reason;
    }
}