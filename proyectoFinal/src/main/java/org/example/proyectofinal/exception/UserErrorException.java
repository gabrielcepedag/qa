package org.example.proyectofinal.exception;

import org.example.proyectofinal.utils.response.ApiResponse;
import org.springframework.http.ResponseEntity;

public class UserErrorException extends RuntimeException{

    private ResponseEntity<ApiResponse> apiResponse;

    public UserErrorException(ResponseEntity<ApiResponse> apiResponse) {
        super();
        this.apiResponse = apiResponse;
    }

    public UserErrorException(String message) {
        super(message);
    }
    public ResponseEntity<ApiResponse> getApiResponse() {
        return apiResponse;
    }

}