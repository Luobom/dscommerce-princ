package com.w3lsolucoes.dscommerceprinc.dto;

import com.w3lsolucoes.dscommerceprinc.entities.User;

public record ClientDTO(
        Long id,
        String name
) {

        public ClientDTO(Long id, String name) {
            this.id = id;
            this.name = name;
        }

        public ClientDTO(User entity) {
            this(entity.getId(), entity.getName());
        }

}
