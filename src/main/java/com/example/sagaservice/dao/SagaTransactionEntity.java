package com.example.sagaservice.dao;

import com.example.sagaservice.enums.Status;
import com.example.sagaservice.enums.Step;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "saga_transactions")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SagaTransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;

    @Enumerated(EnumType.STRING)
    private Step currentStep;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime created_at;

}

