package com.uti.svcorders.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @Column(name = "dish_id", nullable = false)
    private Long dishId;

    @Column(name = "customer_name", nullable = false, length = 100)
    private String customerName;

    @Column(nullable = false)
    private Integer quantity;

    // Precio unitario histórico obtenido desde svc-menu (para evitar que si el menú cambia de precio, se altere el historial)
    @Column(name = "unit_price", nullable = false)
    private Double unitPrice;

    // Calculado automáticamente en el Service: (quantity * unitPrice)
    @Column(name = "total_price", nullable = false)
    private Double totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private OrderStatus status; // PENDING, CONFIRMED, DELIVERED, CANCELLED

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}