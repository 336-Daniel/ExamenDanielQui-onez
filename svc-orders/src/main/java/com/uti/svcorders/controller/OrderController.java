package com.uti.svcorders.controller;

import com.uti.svcorders.dto.OrderRequest;
import com.uti.svcorders.dto.OrderResponse;
import com.uti.svcorders.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    // Retorna todas las órdenes con datos del plato de svc-menu
    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        log.info("GET /api/orders - Fetching all orders");
        return ResponseEntity.ok(orderService.getAllOrders());
    }


    // Retorna una orden específica enriquecida con datos del plato
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long id) {
        log.info("GET /api/orders/{} - Fetching order by id", id);
        return ResponseEntity.ok(orderService.getOrderById(id));
    }


    // Crea una nueva orden

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(
            @Valid @RequestBody OrderRequest request) {
        log.info("POST /api/orders - Creating order for dish id: {}", request.getDishId());
        OrderResponse createdOrder = orderService.createOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }
}