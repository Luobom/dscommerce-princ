package com.w3lsolucoes.dscommerceprinc.repositories;

import com.w3lsolucoes.dscommerceprinc.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
