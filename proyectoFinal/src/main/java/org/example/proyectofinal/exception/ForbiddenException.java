package org.example.proyectofinal.exception;

import lombok.Getter;
import org.example.proyectofinal.utils.response.ApiResponse;
import org.example.proyectofinal.utils.response.CustResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class ForbiddenException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    private ResponseEntity<ApiResponse> apiResponse;
    private CustResponseBuilder custResponseBuilder = new CustResponseBuilder();

    public ForbiddenException() {
        super();
        setApiResponse();
    }

    public ForbiddenException(String message) {
        super();
        setApiResponse(message);
    }

    private void setApiResponse(String message) {
//        String message = String.format("%s not found with %s: '%s'", resourceName, fieldName, fieldValue);
        System.out.println("setApiResponse en ForbiddenException\n");
        apiResponse = custResponseBuilder.buildResponse(HttpStatus.FORBIDDEN.value(), message);
    }

    private void setApiResponse() {
//        String message = String.format("%s not found with %s: '%s'", resourceName, fieldName, fieldValue);
        System.out.println("setApiResponse en ForbiddenException\n");
        apiResponse = custResponseBuilder.buildResponse(HttpStatus.FORBIDDEN.value(), "You don't have permission to access this resource");
    }

}
