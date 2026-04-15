package com.example.sagaservice.client;

import com.example.sagaservice.model.OrderDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "order", url = "http://localhost:8081/order")
public interface OrderClient {

    @PostMapping("/create")
    ResponseEntity<OrderDto> create(@RequestBody OrderDto orderDto);

    @PostMapping("/changeStatus")
    ResponseEntity<OrderDto> changeStatus(@RequestBody OrderDto orderDto);
}
