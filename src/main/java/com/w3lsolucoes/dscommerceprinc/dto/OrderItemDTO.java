package com.w3lsolucoes.dscommerceprinc.dto;

import com.w3lsolucoes.dscommerceprinc.entities.OrderItem;

public record OrderItemDTO(
        Long productId,
        String name,
        Double price,
        Integer quantity,
        Double subtotal

) {

    public OrderItemDTO(Long productId, String name, Double price, Integer quantity) {
        this(productId, name, price, quantity, price * quantity);
    }

    public OrderItemDTO(OrderItem entity) {
        this(entity.getProduct().getId(), entity.getProduct().getName(), entity.getPrice(), entity.getQuantity());
    }

}
