package com.w3lsolucoes.dscommerceprinc.dto;

import com.w3lsolucoes.dscommerceprinc.entities.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record ProductDTO(
        Long id,
        @Size(min = 3, max = 80, message = "O nome do produto deve ter entre 3 e 80 caracteres")
        @NotBlank(message = "Campo obrigatório")
        String name,
        @Size(min = 10, message = "A descrição do produto deve ter no mínimo 10 caracteres")
        @NotBlank(message = "Campo obrigatório")
        String description,
        @Positive(message = "O preço deve ser um valor positivo")
        Double price,
        String imgUrl
) {
    public ProductDTO(Product entity) {
        this(entity.getId(), entity.getName(), entity.getDescription(), entity.getPrice(), entity.getImgUrl());
    }
}

