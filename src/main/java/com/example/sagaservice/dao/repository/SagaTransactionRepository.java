package com.example.sagaservice.dao.repository;

import com.example.sagaservice.dao.SagaTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SagaTransactionRepository extends JpaRepository<SagaTransactionEntity, Long> {
}
