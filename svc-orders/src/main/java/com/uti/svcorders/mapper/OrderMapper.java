package com.uti.svcorders.mapper;

import com.uti.svcorders.dto.DishResponse;
import com.uti.svcorders.dto.OrderRequest;
import com.uti.svcorders.dto.OrderResponse;
import com.uti.svcorders.model.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {


    public Order toEntity(OrderRequest request) {
        return Order.builder()
                .dishId(request.getDishId())
                .customerName(request.getCustomerName())
                .quantity(request.getQuantity())
                .build();
    }


    public OrderResponse toResponse(Order order) {
               return OrderResponse.builder()
                .id(order.getId())
                .dishId(order.getDishId())
                .customerName(order.getCustomerName())
                .quantity(order.getQuantity())
                .totalPrice(order.getTotalPrice())
                .status(order.getStatus())
                .createdAt(order.getCreatedAt())
                .unitPrice(order.getUnitPrice())
                .build();
    }

    public OrderResponse toResponseWithDish(Order order, DishResponse dish) {
        OrderResponse response = toResponse(order);

        if (dish != null) {
            response.setName(dish.getName());
            response.setDescription(dish.getDescription());
            response.setUnitPrice(dish.getPrice());

        }
        return response;
    }
}