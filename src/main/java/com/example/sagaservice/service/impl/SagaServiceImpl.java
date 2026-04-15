package com.example.sagaservice.service.impl;

import com.example.sagaservice.client.OrderClient;
import com.example.sagaservice.client.PaymentClient;
import com.example.sagaservice.dao.SagaTransactionEntity;
import com.example.sagaservice.dao.repository.SagaTransactionRepository;
import com.example.sagaservice.enums.Status;
import com.example.sagaservice.enums.Step;
import com.example.sagaservice.model.OrderDto;
import com.example.sagaservice.model.PaymentDto;
import com.example.sagaservice.service.SagaService;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class SagaServiceImpl implements SagaService {

    private final SagaTransactionRepository sagaTransactionRepository;
    private final OrderClient orderClient;
    private final PaymentClient paymentClient;

    @Override
    @CircuitBreaker(name = "createOrder", fallbackMethod = "orderFallback")
    public void createOrder(OrderDto orderDto) {
        log.info("Starting saga for order");

        OrderDto createdOrder = createOrderStep(orderDto);

        if (createdOrder == null) {
            log.error("Order creation failed - null response");
            return;
        }

        if (createdOrder.getStatus() != Status.PENDING) {
            handleOrderFailure(createdOrder);
            return;
        }

        handlePaymentFlow(createdOrder);
    }

    // =========================
    // ORDER STEP
    // =========================
    private OrderDto createOrderStep(OrderDto orderDto) {
        try {
            OrderDto response = orderClient.create(orderDto).getBody();

            if (response == null) {
                throw new RuntimeException("Order service returned null");
            }

            logSaga(response.getId(), Step.ORDER_PENDING, response.getStatus());
            return response;

        } catch (FeignException e) {
            log.error("Order service error: {}", e.getMessage());
            throw e;
        }
    }

    // =========================
    // PAYMENT FLOW
    // =========================
    private void handlePaymentFlow(OrderDto order) {
        try {
            PaymentDto payment = paymentClient.create(
                    PaymentDto.builder()
                            .orderId(order.getId())
                            .amount(order.getPrice())
                            .build()
            ).getBody();

            if (payment == null) {
                throw new RuntimeException("Payment service returned null");
            }

            if (payment.getStatus() == Status.CONFIRMED) {
                handlePaymentSuccess(order, payment);
            } else {
                handlePaymentFailure(order, payment);
            }

        } catch (FeignException e) {
            log.error("Payment service error: {}", e.getMessage());
            handlePaymentFailure(order, null);
        }
    }

    private void handlePaymentSuccess(OrderDto order, PaymentDto payment) {
        logSaga(order.getId(), Step.PAYMENT_CONFIRMED, payment.getStatus());

        order.setStatus(Status.CONFIRMED);
        OrderDto updated = orderClient.changeStatus(order).getBody();

        logSaga(updated.getId(), Step.ORDER_CONFIRMED, updated.getStatus());
    }

    private void handlePaymentFailure(OrderDto order, PaymentDto payment) {
        logSaga(order.getId(), Step.PAYMENT_CANCELLED,
                Objects.nonNull(payment) ? payment.getStatus() : Status.CANCELLED);

        order.setStatus(Status.CANCELLED);
        orderClient.changeStatus(order);
    }

    private void handleOrderFailure(OrderDto order) {
        logSaga(order.getId(), Step.ORDER_CANCELLED, order.getStatus());

        order.setStatus(Status.CANCELLED);
        orderClient.changeStatus(order);
    }

    // =========================
    // LOGGING
    // =========================
    @Override
    public void log(Long orderId, Step step, Status status) {
        logSaga(orderId, step, status);
    }

    private void logSaga(Long orderId, Step step, Status status) {
        SagaTransactionEntity entity = SagaTransactionEntity.builder()
                .orderId(orderId)
                .currentStep(step)
                .status(status)
                .created_at(LocalDateTime.now())
                .build();

        sagaTransactionRepository.save(entity);

        log.info("Saga log saved | orderId={} | step={} | status={}",
                orderId, step, status);
    }

    // =========================
    // FALLBACK
    // =========================
    public void orderFallback(OrderDto orderDto, Throwable t) {
        log.error("Fallback triggered for orderId={} reason={}",
                orderDto != null ? orderDto.getId() : null,
                t.toString());
    }
}