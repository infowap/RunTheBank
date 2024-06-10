package com.infowap.RunTheBank.Exceptions;

public class AccountServiceException extends RuntimeException {
    public AccountServiceException(String message) {
        super(message);
    }
}
