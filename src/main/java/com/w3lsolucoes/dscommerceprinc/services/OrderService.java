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
    OrderRepository orderRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    UserService userService;


    @Transactional(readOnly = true)
    public OrderDTO findById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Resource not found: " + id));
        return new OrderDTO(order);
    }


    @Transactional
    public OrderDTO save( OrderDTO dto) {

        Order order = new Order();
        order.setMoment(Instant.now());
        order.setOrderStatus(OrderStatus.WAITING_PAYMENT);

        User user = userService.authenticated();
        order.setClient(user);

        for (OrderItemDTO itemDTO : dto.items()) {
            Product product = productRepository.getReferenceById(itemDTO.productId());
            // debug
            System.out.println(
                    "id: " + product.getId() +
                    "\n name: " + product.getName() +
                    "\n description: " + product.getDescription() +
                    "\n price: " + product.getPrice() +
                    "\n quantity: " + itemDTO.quantity() +
                    "\n imgUrl: " + product.getImgUrl()
            );


            OrderItem orderItem = new OrderItem(order, product, itemDTO.quantity(), itemDTO.price());
            order.getItems().add(orderItem);
        }

        orderRepository.save(order);
        orderItemRepository.saveAll(order.getItems());

        return new OrderDTO(order);

    }
}
