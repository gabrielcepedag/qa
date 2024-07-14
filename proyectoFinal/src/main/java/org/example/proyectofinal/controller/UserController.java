package org.example.proyectofinal.controller;

import jakarta.validation.Valid;
import org.example.proyectofinal.dto.request.UserRequest;
import org.example.proyectofinal.dto.response.UserResponse;
import org.example.proyectofinal.entity.Product;
import org.example.proyectofinal.entity.User;
import org.example.proyectofinal.service.UserService;
import org.example.proyectofinal.utils.response.ApiResponse;
import org.example.proyectofinal.utils.response.CustResponseBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping
public class UserController {
    private final UserService userService;
    private ModelMapper modelMapper;
    private CustResponseBuilder custResponseBuilder;

    public UserController(UserService userService, ModelMapper modelMapper, CustResponseBuilder custResponseBuilder) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.custResponseBuilder = custResponseBuilder;
    }

    //===================================== API =========================================

    @GetMapping("/api/v1/users")
    private ResponseEntity<?> getAllUsers() {
        List<User> users = userService.findAllUsers();
        List<UserResponse> usersResponse = Arrays.asList(modelMapper.map(users, UserResponse[].class));
        ResponseEntity<ApiResponse> response = custResponseBuilder.buildResponse(HttpStatus.OK.value(), usersResponse);

        return response;
    }

    @GetMapping("/api/v1/users/{id}")
    private ResponseEntity<?> getUserById(@PathVariable Long id) {
        User user = userService.findOneById(id);
        UserResponse userResponse = modelMapper.map(user, UserResponse.class);
        ResponseEntity<ApiResponse> response = custResponseBuilder.buildResponse(HttpStatus.OK.value(), userResponse);

        return response;
    }

    @PostMapping("/api/v1/users")
    private ResponseEntity<?> addUser(@Valid @RequestBody UserRequest userRequest) {
        User user = userService.createUser(userRequest);
        UserResponse userResponse = modelMapper.map(user, UserResponse.class);
        ResponseEntity<ApiResponse> response = custResponseBuilder.buildResponse(HttpStatus.OK.value(), userResponse);

        return response;
    }

    @DeleteMapping("/api/v1/users/{id}")
    private ResponseEntity<?> deleteUser(@PathVariable Long id) {
        Boolean bool = userService.deleteUserById(id);
        ResponseEntity<ApiResponse> response = custResponseBuilder.buildResponse(HttpStatus.OK.value(), bool);

        return response;
    }

    @PutMapping("/api/v1/users/{id}")
    private ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserRequest userRequest) {
        User user = userService.updateUser(id, userRequest);
        UserResponse userResponse = modelMapper.map(user, UserResponse.class);
        ResponseEntity<ApiResponse> response = custResponseBuilder.buildResponse(HttpStatus.OK.value(), userResponse);

        return response;
    }

    //===================================== VIEW =========================================

    @GetMapping("/users")
    public String manageUsersPage(Model model){
        List<User> users = userService.findAllUsers();

        model.addAttribute("userList", users);
        return "manageUsers";
    }

}
