package com.w3lsolucoes.dscommerceprinc.controllers;

import com.w3lsolucoes.dscommerceprinc.dto.ProductDTO;
import com.w3lsolucoes.dscommerceprinc.dto.ProductMinDTO;
import com.w3lsolucoes.dscommerceprinc.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

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
    public ResponseEntity<Page<ProductMinDTO>> findAll(Pageable pageable) {
        // return ResponseEntity.ok(service.findAll(pageable));
        return ResponseEntity.status(HttpStatus.OK).body(service.findAll(pageable));
    }

    @GetMapping(value = "/search")
    public ResponseEntity<Page<ProductMinDTO>> findByName(@RequestParam(name = "name", defaultValue = " ") String name, Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(service.findByName(name, pageable));
    }

    // @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<ProductDTO> save(@Valid @RequestBody ProductDTO dto) {
        ProductDTO productDTO = service.save(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(productDTO.id()).toUri();
        return ResponseEntity.created(uri).body(productDTO);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> update(@PathVariable Long id, @Valid @RequestBody ProductDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object>/*<Void>*/ delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Product deleted successfully");
        //return ResponseEntity.noContent().build();
    }

}