package com.w3lsolucoes.dscommerceprinc.controllers;

import com.w3lsolucoes.dscommerceprinc.dto.CategoryAndProductDTO;
import com.w3lsolucoes.dscommerceprinc.dto.CategoryDTO;
import com.w3lsolucoes.dscommerceprinc.entities.Category;
import com.w3lsolucoes.dscommerceprinc.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/categories")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping(value = "/havingproducts")
    public ResponseEntity<List<CategoryAndProductDTO>> findCategoriesHavingProducts() {
        return ResponseEntity.ok(service.findCategoriesHavingProducts());
    }

}
