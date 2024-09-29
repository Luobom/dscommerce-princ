package com.w3lsolucoes.dscommerceprinc.dto;

import com.w3lsolucoes.dscommerceprinc.entities.OrderItem;

public record OrderItemDTO(
        Long productId,
        String name,
        Double price,
        String imgUrl,
        Integer quantity,
        Double subtotal

) {

    public OrderItemDTO(Long productId, String name, Double price, String imgUrl, Integer quantity) {
        this(productId, name, price, imgUrl, quantity, price * quantity );
    }

    public OrderItemDTO(OrderItem entity) {
        this(entity.getProduct().getId(), entity.getProduct().getName(), entity.getPrice(), entity.getProduct().getImgUrl(), entity.getQuantity());
    }

}
