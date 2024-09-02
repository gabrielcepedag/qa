package org.example.proyectofinal.security;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {
    public static final List<String> openApiEndpoints = List.of(
//            "/api/register",
//            "/auth/token",
//            "/eureka",
//            "/cart",
            "/api/v1/",
            "/actuator/"
    );

    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
//                    .noneMatch(uri -> request.getURI().getPath().contains(uri));
                    .anyMatch(uri -> request.getURI().getPath().contains(uri));
}
