package com.w3lsolucoes.dscommerceprinc.dto;

import com.w3lsolucoes.dscommerceprinc.entities.Category;

import java.util.Set;
import java.util.stream.Collectors;

public record CategoryAndProductDTO(
        Long id,
        String name,
        Set<ProductMinDTO> products
) {

    public CategoryAndProductDTO(Category entity) {
        this(entity.getId(), entity.getName(), entity.getProducts().stream().map(ProductMinDTO::new).collect(Collectors.toSet()));
    }

}
