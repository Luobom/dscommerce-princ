package com.w3lsolucoes.dscommerceprinc.services;

import com.w3lsolucoes.dscommerceprinc.dto.CategoryAndProductDTO;
import com.w3lsolucoes.dscommerceprinc.dto.CategoryDTO;
import com.w3lsolucoes.dscommerceprinc.entities.Category;
import com.w3lsolucoes.dscommerceprinc.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    // get all categories
    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll() {
        List<Category> list = repository.findAll();
        return list.stream().map(CategoryDTO::new).collect(Collectors.toList());
    }

    // get categories having products
    @Transactional(readOnly = true)
    public List<CategoryAndProductDTO> findCategoriesHavingProducts() {
        List<Category> list = repository.findCategoriesHavingProducts();
        return list.stream().map(CategoryAndProductDTO::new).collect(Collectors.toList());
    }

}
