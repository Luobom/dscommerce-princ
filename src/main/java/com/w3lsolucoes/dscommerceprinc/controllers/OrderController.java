package com.w3lsolucoes.dscommerceprinc.controllers;

import com.w3lsolucoes.dscommerceprinc.dto.OrderDTO;
import com.w3lsolucoes.dscommerceprinc.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/orders")
public class OrderController {

    @Autowired
    OrderService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<OrderDTO> findById(@PathVariable  Long id) {
        // return ResponseEntity.ok(service.findById(id));
        return ResponseEntity.status(HttpStatus.OK).body(service.findById(id));
    }

}