package org.example.proyectofinal.exception;

import org.example.proyectofinal.utils.response.ApiResponse;
import org.example.proyectofinal.utils.response.CustResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private ResponseEntity<ApiResponse> apiResponse;
    private CustResponseBuilder custResponseBuilder = new CustResponseBuilder();

    public UnauthorizedException() {
        super();
        setApiResponse();
    }

    public ResponseEntity<ApiResponse> getApiResponse() {
        return apiResponse;
    }

    public CustResponseBuilder getCustResponseBuilder() {
        return custResponseBuilder;
    }

    private void setApiResponse() {
//        String message = String.format("%s not found with %s: '%s'", resourceName, fieldName, fieldValue);
        System.out.println("setApiResponse en UnauthorizedException\n");
        apiResponse = custResponseBuilder.buildResponse(HttpStatus.UNAUTHORIZED.value(), "You don't have permission to access this resource");
    }
}