package com.w3lsolucoes.dscommerceprinc.services;

import com.w3lsolucoes.dscommerceprinc.dto.OrderDTO;
import com.w3lsolucoes.dscommerceprinc.dto.OrderItemDTO;
import com.w3lsolucoes.dscommerceprinc.entities.*;
import com.w3lsolucoes.dscommerceprinc.repositories.OrderItemRepository;
import com.w3lsolucoes.dscommerceprinc.repositories.OrderRepository;
import com.w3lsolucoes.dscommerceprinc.repositories.ProductRepository;
import com.w3lsolucoes.dscommerceprinc.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class OrderService {

    @Autowired
    UserService userService;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    AuthService authService;

    @Transactional(readOnly = true)
    public OrderDTO findById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Resource not found: " + id));

        // Check if the authenticated user is the owner of the order or is an admin
        authService.validateSelfOrAdmin(order.getClient().getId());

        return new OrderDTO(order);
    }


    @Transactional
    public OrderDTO save( OrderDTO dto) {

        User user = userService.authenticated();
        Order order = new Order();
        order.setClient(user);
        order.setMoment(Instant.now());
        order.setOrderStatus(OrderStatus.WAITING_PAYMENT);

        // with for (option 1)
//        for (OrderItemDTO itemDTO : dto.items()) {
//            Product product = productRepository.getReferenceById(itemDTO.productId());
//            OrderItem orderItem = new OrderItem(order, product, itemDTO.quantity(), product.getPrice());
//            order.getItems().add(orderItem);
//        }


        // with lambda stream API (option 2)
        dto.items().forEach(itemDTO -> {
            Product product = productRepository.getReferenceById(itemDTO.productId());
            OrderItem orderItem = new OrderItem(order, product, itemDTO.quantity(), product.getPrice());
            order.getItems().add(orderItem);
        });

        orderRepository.save(order);
        orderItemRepository.saveAll(order.getItems());

        return new OrderDTO(order);

    }
}
