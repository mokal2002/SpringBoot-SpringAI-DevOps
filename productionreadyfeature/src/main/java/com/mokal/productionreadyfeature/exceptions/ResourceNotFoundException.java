package com.mokal.productionreadyfeature.exceptions;

public class ResourceNotFoundException extends RuntimeException

{
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
