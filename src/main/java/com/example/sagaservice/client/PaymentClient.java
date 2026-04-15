package com.example.sagaservice.client;

import com.example.sagaservice.model.PaymentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "payment", url = "http://localhost:8080/payment")
public interface PaymentClient {

    @PostMapping("/create")
    ResponseEntity<PaymentDto> create(@RequestBody PaymentDto orderDto);

    @PostMapping("/changeStatus")
    ResponseEntity<PaymentDto> changeStatus(@RequestBody PaymentDto orderDto);
}
