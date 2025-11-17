package com.ecommerce.tecnologycenter.services.exceptions;

// Exception que extend de Runtime não precisa do TRY - CATCH
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
