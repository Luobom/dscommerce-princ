package com.w3lsolucoes.dscommerceprinc.dto;

import com.w3lsolucoes.dscommerceprinc.entities.Order;
import com.w3lsolucoes.dscommerceprinc.entities.OrderStatus;

import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

public record OrderDTO(
        Long id,
        Instant moment,
        OrderStatus status,
        ClientDTO client,
        PaymentDTO payment,
        Set<OrderItemDTO> items,
        Double total // novo campo total
) {

    // Construtor principal que calcula o total a partir dos itens
    public OrderDTO(Long id, Instant moment, OrderStatus status, ClientDTO client, PaymentDTO payment, Set<OrderItemDTO> items) {
        this(id, moment, status, client, payment, items, calculateTotal(items));
    }

    // Construtor que aceita uma entidade Order e calcula o total
    public OrderDTO(Order entity) {
        this(
                entity.getId(),
                entity.getMoment(),
                entity.getStatus(),
                new ClientDTO(entity.getClient()),
                entity.getPayment() == null ? null : new PaymentDTO(entity.getPayment()),

                // Mapeia os itens para OrderItemDTO
                entity.getItems().stream().map(OrderItemDTO::new).collect(Collectors.toSet())
        );
    }

    // Métod auxiliar para calcular o total a partir dos subtotais dos itens
    private static Double calculateTotal(Set<OrderItemDTO> items) {
        return items.stream()
                .mapToDouble(OrderItemDTO::subtotal) // Soma os subtotais de todos os itens
                .sum();
    }

}
