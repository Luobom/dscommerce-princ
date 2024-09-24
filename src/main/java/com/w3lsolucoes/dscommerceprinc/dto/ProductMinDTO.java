package com.w3lsolucoes.dscommerceprinc.dto;

import com.w3lsolucoes.dscommerceprinc.entities.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record ProductMinDTO(
        Long id,
        String name,
        Double price,
        String imgUrl
) {
    public ProductMinDTO(Product entity) {
        this(entity.getId(), entity.getName(), entity.getPrice(), entity.getImgUrl());
    }
}

