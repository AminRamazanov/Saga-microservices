package com.example.sagaservice.model.event;

public class PaymentSuccessEvent extends BaseEvent {

    private String orderId;

    public PaymentSuccessEvent() {}

    public PaymentSuccessEvent(String orderId) {
        this.orderId = orderId;
        setAggregateId(orderId);
    }

    public String getOrderId() {
        return orderId;
    }
}