package com.uti.svcorders.service.Impl;

import com.uti.svcorders.client.MenuWebClient;
import com.uti.svcorders.dto.DishResponse;
import com.uti.svcorders.dto.OrderRequest;
import com.uti.svcorders.dto.OrderResponse;
import com.uti.svcorders.exception.BusinessRulesException;
import com.uti.svcorders.exception.ResourceNotfoundException;
import com.uti.svcorders.mapper.OrderMapper;
import com.uti.svcorders.model.Order;
import com.uti.svcorders.model.OrderStatus;
import com.uti.svcorders.repository.OrderRepository;
import com.uti.svcorders.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final MenuWebClient webClient;

    @Override
    @Transactional
    public OrderResponse createOrder(OrderRequest request) {
        log.info("Creando una orden para el cliente: {} - Plato ID: {}", request.getCustomerName(), request.getDishId());

        // 1. Obtener detalles del plato vía WebClient (valida existencia automáticamente)
        log.info("Consultando disponibilidad y precio del plato en svc-menu vía WebClient...");
        DishResponse dishDetails = webClient.getDishById(request.getDishId());

        // 2. Validación de regla de negocio (lanzar 400 Bad Request si available es false)
        if (dishDetails.getAvailable() == null || !dishDetails.getAvailable()) {
            throw new BusinessRulesException(
                    "El plato '" + dishDetails.getName() + "' (ID: " + request.getDishId() + ") no se encuentra disponible actualmente.");
        }

        Order order = orderMapper.toEntity(request);

        order.setUnitPrice(dishDetails.getPrice());
        order.setTotalPrice(dishDetails.getPrice() * request.getQuantity());
        order.setStatus(OrderStatus.PENDING);
        order.setCreatedAt(LocalDateTime.now());

        Order savedOrder = orderRepository.save(order);
        log.info("Orden registrada exitosamente con ID: {}", savedOrder.getId());

        return orderMapper.toResponseWithDish(savedOrder, dishDetails);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponse getOrderById(Long id) {
        log.info("Obteniendo orden con ID: {}", id);

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotfoundException(
                        "Orden no encontrada con id: " + id
                ));

        DishResponse dishDetails = null;
        try {
            dishDetails = webClient.getDishById(order.getDishId());
        } catch (Exception ex) {
            log.warn("No se logró obtener el detalle del plato para la orden ID: {}", id);
        }

        return orderMapper.toResponseWithDish(order, dishDetails);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponse> getAllOrders() {
        log.info("Obteniendo todas las órdenes registradas");

        return orderRepository.findAll()
                .stream()
                .map(order -> {
                    try {

                        DishResponse dish = webClient.getDishById(order.getDishId());
                        return orderMapper.toResponseWithDish(order, dish);
                    } catch (Exception ex) {
                        log.warn("No se logró obtener el detalle del plato para la orden ID: {}", order.getId());
                        return orderMapper.toResponse(order);
                    }
                })
                .collect(Collectors.toList());
    }
}