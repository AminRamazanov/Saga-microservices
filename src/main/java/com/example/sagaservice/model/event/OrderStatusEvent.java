package com.example.sagaservice.model.event;

import lombok.Data;
import org.example.enums.OrderStatus;

import java.math.BigDecimal;

@Data
public class OrderStatusEvent {
    private Long id;

    private Long userId;

    private BigDecimal price;

    private OrderStatus orderStatus;
}
