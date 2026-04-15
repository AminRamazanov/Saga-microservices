package com.example.sagaservice.controller;

import com.example.sagaservice.model.OrderDto;
import com.example.sagaservice.service.SagaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/saga")
@RequiredArgsConstructor
public class SagaController {
    private final SagaService sagaService;

    @PostMapping
    public void createOrder(@RequestBody OrderDto orderDto) {
        sagaService.createOrder(orderDto);
    }

    @GetMapping("/error500")
    public ResponseEntity<String> error500() {
        throw new RuntimeException("Simulated server error");
    }
}
