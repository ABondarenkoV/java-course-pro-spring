package org.example.exception;

public class IntegrationException extends RuntimeException {

    public IntegrationException(Integer externalCode, String message, String externalMessage) {
        super(message);
    }
}
