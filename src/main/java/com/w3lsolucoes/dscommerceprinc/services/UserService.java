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
        List<UserDetailProjection> list = userRepository.searchUserAndRole(username);
        if (list.isEmpty()) {
            throw new UsernameNotFoundException("Email not found");
        }
        var user = new User();
        BeanUtils.copyProperties(list.getFirst(), user);
        user.setEmail(username);
        if (list.getFirst().getRoleId() != null) {
            list.forEach(x -> user.addRole(new Role(x.getRoleId(), x.getAuthority())));
        }
        return user;
    }

}
