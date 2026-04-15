package com.example.sagaservice.model;


import com.example.sagaservice.enums.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderDto {
    private Long id;

    private Integer quantity;

    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private Status status;

}

