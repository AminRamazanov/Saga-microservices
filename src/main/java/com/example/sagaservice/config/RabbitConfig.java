package com.example.sagaservice.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
        private static final String SAGA_EXCHANGE = "saga.exchange";
        private static final String SAGA_DLQ_EXCHANGE = "saga.dlq.exchange";

        private static final String ORDER_CREATED_QUEUE = "order.created.queue";
        private static final String ORDER_CREATED_DLQ_QUEUE = "order.created.dlq.queue";

        private static final String ORDER_CREATED_ROUTING_KEY = "order.created.key";
        private static final String ORDER_CREATED_DLQ_ROUTING_KEY = "order.created.dlq.key";

    @Bean
    public TopicExchange sagaExchange() {
        return new TopicExchange(SAGA_EXCHANGE);
    }

    @Bean
    public TopicExchange sagaDlqExchange() {
        return new TopicExchange(SAGA_DLQ_EXCHANGE);
    }

    @Bean
    public Queue orderCreatedQueue() {
        return QueueBuilder.durable(ORDER_CREATED_QUEUE)
                .withArgument("x-dead-letter-exchange", SAGA_DLQ_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", ORDER_CREATED_DLQ_ROUTING_KEY)
                .build();
    }

    @Bean
    public Queue orderCreatedDlqQueue() {
        return QueueBuilder.durable(ORDER_CREATED_DLQ_QUEUE).build();
    }

    @Bean
    public Binding orderCreatedBinding() {
        return BindingBuilder
                .bind(orderCreatedQueue())
                .to(sagaExchange())
                .with(ORDER_CREATED_ROUTING_KEY);
    }

    @Bean
    public Binding orderCreatedDlqBinding() {
        return BindingBuilder
                .bind(orderCreatedDlqQueue())
                .to(sagaDlqExchange())
                .with(ORDER_CREATED_DLQ_ROUTING_KEY);
    }
}