package org.example.proyectofinal.exception;

import org.example.proyectofinal.utils.response.ApiResponse;
import org.example.proyectofinal.utils.response.CustResponseBuilder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
@Getter
public class BadRequestException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private ResponseEntity<ApiResponse> apiResponse;
    private CustResponseBuilder custResponseBuilder = new CustResponseBuilder();

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
        setApiResponse(message, cause);
    }

    public BadRequestException(String message) {
        super(message);
        setApiResponse(message);
    }

    public BadRequestException(String message, String extra) {
        super(message);
        setApiResponse(message, extra);
    }

    private void setApiResponse(String message, String extra) {
        System.out.println("setApiResponse en BadRequestException\n");
        apiResponse = custResponseBuilder.buildResponse(HttpStatus.BAD_REQUEST.value(), message, extra);
    }

    private void setApiResponse(String message) {
        System.out.println("setApiResponse en BadRequestException\n");
        apiResponse = custResponseBuilder.buildResponse(HttpStatus.BAD_REQUEST.value(), message);
    }

    private void setApiResponse(String message, Throwable cause) {
        System.out.println("setApiResponse en BadRequestException\n");
        apiResponse = custResponseBuilder.buildResponse(HttpStatus.BAD_REQUEST.value(), message, cause.getCause().getMessage());
    }

}