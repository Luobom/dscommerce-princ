package com.w3lsolucoes.dscommerceprinc.repositories;

import com.w3lsolucoes.dscommerceprinc.entities.OrderItem;
import com.w3lsolucoes.dscommerceprinc.entities.OrderItemPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPK> {

}
