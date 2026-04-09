package com.mokal.productionreadyfeature.advice;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ApiError {

    private LocalDateTime timestamp;
    private String message;
    private HttpStatus status;

    // Default constructor
    public ApiError() {
        this.timestamp = LocalDateTime.now();
    }

    // Main constructor (USED everywhere)
    public ApiError(String message, HttpStatus status) {
        this.timestamp = LocalDateTime.now();
        this.message = message;
        this.status = status;
    }
}