package org.example.proyectofinal.service;

import org.example.proyectofinal.cons.ERole;
import org.example.proyectofinal.dto.request.UserRequest;
import org.example.proyectofinal.entity.Product;
import org.example.proyectofinal.entity.User;
import org.example.proyectofinal.exception.BadRequestException;
import org.example.proyectofinal.exception.ResourceNotFoundException;
import org.example.proyectofinal.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private ModelMapper modelMapper;

    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public User findOneById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null || user.isDeleted()) {
            throw new ResourceNotFoundException(User.class.getSimpleName(), "ID", id);
        }
        return user;
    }

    public User findOneByUsername(String username) {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null || user.isDeleted()) {
            throw new ResourceNotFoundException(User.class.getSimpleName(), "USERNAME", username);
        }
        return user;
    }

    public List<User> findAllUsers() {
        return userRepository.findAllByDeletedIsFalse();
    }

    public User createUser(UserRequest userRequest) {
        //TODO: Validar roles
        try {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            userRequest.setPassword(encoder.encode(userRequest.getPassword()));
            User user = new User();
            user.setId(null);
            modelMapper.map(userRequest, user);
            if (userRequest.getRole() != null) {
                user.setRole(ERole.valueOf(userRequest.getRole()));
            }
            return userRepository.save(user);
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }

    public boolean deleteUserById(Long id) {
        User user = findOneById(id);
        user.setDeleted(true);
        userRepository.save(user);
        return true;
    }

    public User updateUser(Long id, UserRequest userRequest) {
        User user = findOneById(id);
        modelMapper.map(userRequest, user);
        user.setId(id);

        if (userRequest.getRole() != null) {
            user.setRole(ERole.valueOf(userRequest.getRole()));
        }

        try {
            return userRepository.save(user);
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }
}
