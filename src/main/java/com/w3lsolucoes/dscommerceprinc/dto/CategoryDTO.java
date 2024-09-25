package com.w3lsolucoes.dscommerceprinc.dto;

import com.w3lsolucoes.dscommerceprinc.entities.Category;

public record CategoryDTO(
        Long id,
        String name
) {

    public CategoryDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public CategoryDTO(Category entity) {
        this(entity.getId(), entity.getName());
    }

}
