package org.example.proyectofinal.security;

import org.example.proyectofinal.entity.User;
import org.example.proyectofinal.repository.UserRepository;
import org.example.proyectofinal.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Entering in loadUserByUsername Method...");
        User user = userService.findOneByUsername(username);
        System.out.println("User Authenticated Successfully..!!!");

        return new CustomUserDetails(user);
    }
}
