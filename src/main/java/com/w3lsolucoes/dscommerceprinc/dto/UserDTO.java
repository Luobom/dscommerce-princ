package com.w3lsolucoes.dscommerceprinc.dto;

import com.w3lsolucoes.dscommerceprinc.entities.Role;
import com.w3lsolucoes.dscommerceprinc.entities.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public record UserDTO(
        Long id,
        String name,
        String email,
        String phone,
        LocalDate birthDate,
        List<String> roles

) {
        public UserDTO(User entity) {
                this(entity.getId(), entity.getName(), entity.getEmail(), entity.getPhone(), entity.getBirthDate(),
                        entity.getRoles().stream().map(Role::getAuthority).toList());
        }
}
