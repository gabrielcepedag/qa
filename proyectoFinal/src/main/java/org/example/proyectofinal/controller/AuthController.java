package org.example.proyectofinal.controller;

import org.example.proyectofinal.dto.request.AuthRequestDTO;
import org.example.proyectofinal.dto.response.AuthResponse;
import org.example.proyectofinal.dto.response.UserResponse;
import org.example.proyectofinal.exception.UnauthorizedException;
import org.example.proyectofinal.security.JwtService;
import org.example.proyectofinal.service.UserService;
import org.example.proyectofinal.utils.response.ApiResponse;
import org.example.proyectofinal.utils.response.CustResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping
public class AuthController {
    private JwtService jwtService;
    private AuthenticationManager authenticationManager;
    private CustResponseBuilder custResponseBuilder;

    public AuthController(JwtService jwtService, AuthenticationManager authenticationManager, CustResponseBuilder custResponseBuilder) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.custResponseBuilder = custResponseBuilder;
    }

    @PostMapping("/api/v1/auth")
    public ResponseEntity<ApiResponse> AuthenticateAndGetToken(@RequestBody AuthRequestDTO authRequestDTO){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword()));

        if(authentication.isAuthenticated()){
            System.out.println("El usuario está autenticado");
            AuthResponse response =
            AuthResponse.builder()
                    .role(authentication.getAuthorities())
                    .username(authentication.getName())
                    .accessToken(jwtService.GenerateToken(authRequestDTO.getUsername()))
                    .build();

            return custResponseBuilder.buildResponse(HttpStatus.OK.value(), "Usuario logueado correctamente!", response);
        } else {
            System.out.println("El usuario NOOOO está autenticado");
            throw new UnauthorizedException();
        }
    }
}
