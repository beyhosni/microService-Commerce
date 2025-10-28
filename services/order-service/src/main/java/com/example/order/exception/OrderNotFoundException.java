package com.example.order.exception;

/**
 * Exception levée lorsqu'une commande n'est pas trouvée.
 */
public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(String message) {
        super(message);
    }

    public OrderNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
