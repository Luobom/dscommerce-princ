package com.w3lsolucoes.dscommerceprinc.controllers;

import com.w3lsolucoes.dscommerceprinc.dto.ProductDTO;
import com.w3lsolucoes.dscommerceprinc.entities.Product;
import com.w3lsolucoes.dscommerceprinc.repositories.ProductRepository;
import com.w3lsolucoes.dscommerceprinc.services.ProductService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

    @Autowired
    ProductService service;


    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable  Long id) {
        // return ResponseEntity.ok(service.findById(id));
        return ResponseEntity.status(HttpStatus.OK).body(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<Page<ProductDTO>> findAll(Pageable pageable) {
        // return ResponseEntity.ok(service.findAll(pageable));
        return ResponseEntity.status(HttpStatus.OK).body(service.findAll(pageable));
    }

    @GetMapping(value = "/search")
    public ResponseEntity<Page<ProductDTO>> findByName(@RequestParam(name = "name", defaultValue = " ") String name, Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(service.findByName(name, pageable));
    }

    @PostMapping
    public ResponseEntity<ProductDTO> save(@Valid @RequestBody ProductDTO dto) {
        ProductDTO productDTO = service.save(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(productDTO.id()).toUri();
        return ResponseEntity.created(uri).body(productDTO);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> update(@PathVariable Long id, @Valid @RequestBody ProductDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object>/*<Void>*/ delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Product deleted successfully");
        //return ResponseEntity.noContent().build();
    }

}