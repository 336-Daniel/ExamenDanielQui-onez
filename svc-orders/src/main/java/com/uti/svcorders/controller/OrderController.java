package com.uti.svcorders.controller;

import com.uti.svcorders.dto.OrderRequest;
import com.uti.svcorders.dto.OrderResponse;
import com.uti.svcorders.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    // 1. POST /api/orders (Crear una orden -> 201 Created)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse createOrder(@Valid @RequestBody OrderRequest request) {
        log.info("REST request to create an order for customer: {}", request.getCustomerName());
        return orderService.createOrder(request);
    }

    // 2. GET /api/orders/{id} (Obtener orden por ID -> 200 OK)
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderResponse getOrderById(@PathVariable Long id) {
        log.info("REST request to get order by id: {}", id);
        return orderService.getOrderById(id);
    }

    // 3. GET /api/orders (Listar todas las órdenes -> 200 OK)
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResponse> getAllOrders() {
        log.info("REST request to get all orders");
        return orderService.getAllOrders();
    }
}