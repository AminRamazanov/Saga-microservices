package com.example.sagaservice.model.event;

import java.time.LocalDateTime;
import java.util.UUID;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type" // JSON-da olacaq field
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = OrderCreatedEvent.class, name = "ORDER_CREATED"),
        @JsonSubTypes.Type(value = PaymentSuccessEvent.class, name = "PAYMENT_SUCCESS"),
        @JsonSubTypes.Type(value = PaymentFailedEvent.class, name = "PAYMENT_FAILED")
})
public abstract class BaseEvent implements Event {

    private String eventId;
    private String aggregateId;
    private LocalDateTime createdAt;

    public BaseEvent() {
        this.eventId = UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
    }

    @Override
    public String getEventId() {
        return eventId;
    }

    public String getAggregateId() {
        return aggregateId;
    }

    public void setAggregateId(String aggregateId) {
        this.aggregateId = aggregateId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}