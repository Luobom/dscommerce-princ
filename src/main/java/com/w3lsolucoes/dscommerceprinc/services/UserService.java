package com.w3lsolucoes.dscommerceprinc.services;

import com.w3lsolucoes.dscommerceprinc.dto.UserDTO;
import com.w3lsolucoes.dscommerceprinc.entities.Role;
import com.w3lsolucoes.dscommerceprinc.entities.User;
import com.w3lsolucoes.dscommerceprinc.projections.UserDetailProjection;
import com.w3lsolucoes.dscommerceprinc.repositories.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<UserDetailProjection> userDetailProjections = userRepository.searchUserAndRole(username);
        if (userDetailProjections.isEmpty()) {
            throw new UsernameNotFoundException("Email not found");
        }
        var user = new User();
        BeanUtils.copyProperties(userDetailProjections.getFirst(), user);
        user.setEmail(username);

        if (userDetailProjections.getFirst().getRoleId() != null) {
            userDetailProjections.forEach(x -> user.addRole(new Role(x.getRoleId(), x.getAuthority())));
        } else {
            System.out.println("User has no roles");
        }
        return user;
    }

    // Return authenticated user
    @Transactional(readOnly = true)
    protected User authenticated() {
        try {
            //return (User) org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Jwt jwtPrincipal = (Jwt) authentication.getPrincipal();
            String username = jwtPrincipal.getClaim("username");
            return userRepository.findByEmail(username).get();

        } catch (Exception e) {
            throw new UsernameNotFoundException("User not found");
        }
    }

    // Return authenticated user DTO
    @Transactional(readOnly = true)
    public UserDTO authenticatedDTO() {
        return new UserDTO(authenticated());
    }

}
