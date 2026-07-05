package com.uti.svcorders.dto;


import com.uti.svcorders.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponse {

    private Long id;
    private Long dishId;
    private String customerName;
    private Integer quantity;

    private Double totalPrice;
    private OrderStatus status;
    private LocalDateTime createdAt;

    // datos del plato
    private String name;
    private String description;
    private Double unitPrice;

}