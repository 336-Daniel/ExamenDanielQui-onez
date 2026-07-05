package com.uti.svcorders.service;

import com.uti.svcorders.dto.OrderRequest;
import com.uti.svcorders.dto.OrderResponse;

import java.util.List;

public interface OrderService {

    //  (Crear una orden consultando disponibilidad y precio en svc-menu)
    OrderResponse createOrder(OrderRequest request);

    //  (Obtener una orden específica detallando los datos del plato)
    OrderResponse getOrderById(Long id);

    //  (Listar todas las órdenes registradas)
    List<OrderResponse> getAllOrders();
}