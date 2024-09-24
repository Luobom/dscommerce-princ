package com.w3lsolucoes.dscommerceprinc.controllers;

import com.w3lsolucoes.dscommerceprinc.dto.UserDTO;
import com.w3lsolucoes.dscommerceprinc.entities.User;
import com.w3lsolucoes.dscommerceprinc.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService service;

    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    @GetMapping(value = "/profile")
    public ResponseEntity<UserDTO> profile() {
        return ResponseEntity.ok(service.authenticatedDTO());
    }

}
