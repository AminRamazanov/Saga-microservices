package com.example.sagaservice.service;

import com.example.sagaservice.enums.Status;
import com.example.sagaservice.enums.Step;
import com.example.sagaservice.model.OrderDto;

public interface SagaService {
    void createOrder(OrderDto orderDto);

    void log(Long orderId, Step step, Status status);
}
