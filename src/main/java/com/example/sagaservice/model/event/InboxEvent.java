package com.example.sagaservice.model.event;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InboxEvent {
    private String eventId;

    private String type;

    private LocalDateTime receivedAt;
}
