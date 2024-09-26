package com.w3lsolucoes.dscommerceprinc.services;

import com.w3lsolucoes.dscommerceprinc.dto.OrderDTO;
import com.w3lsolucoes.dscommerceprinc.entities.Order;
import com.w3lsolucoes.dscommerceprinc.repositories.OrderRepository;
import com.w3lsolucoes.dscommerceprinc.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    @Autowired
    OrderRepository repository;


    @Transactional(readOnly = true)
    public OrderDTO findById(Long id) {
        Order order = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Resource not found: " + id));
        return new OrderDTO(order);
    }


}
