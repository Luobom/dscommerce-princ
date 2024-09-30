package com.w3lsolucoes.dscommerceprinc.services;

import com.w3lsolucoes.dscommerceprinc.entities.User;
import com.w3lsolucoes.dscommerceprinc.services.exceptions.ForbiddenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserService userService;

    public void validateSelfOrAdmin(Long userId) {
        User user = userService.authenticated();
        if (!user.getId().equals(userId) && !user.hasRole("ROLE_ADMIN")) {
            throw new ForbiddenException("Access denied");
        }
    }


}
