package com.example.sagaservice.model.event;

public class OrderCompletedEvent extends BaseEvent {

    private String orderId;

    public OrderCompletedEvent() {}

    public OrderCompletedEvent(String orderId) {
        this.orderId = orderId;
        setAggregateId(orderId);
    }

    public String getOrderId() {
        return orderId;
    }
}