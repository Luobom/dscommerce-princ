package com.w3lsolucoes.dscommerceprinc.services;

import com.w3lsolucoes.dscommerceprinc.entities.Role;
import com.w3lsolucoes.dscommerceprinc.entities.User;
import com.w3lsolucoes.dscommerceprinc.projections.UserDetailProjection;
import com.w3lsolucoes.dscommerceprinc.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
/
            // debug
            user.getRoles().forEach(x -> System.out.println("Role: " + x.getAuthority()));
        } else {
            System.out.println("User has no roles");
        }
        return user;
    }

}
