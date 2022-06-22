package com.digital.banking.exceptions;

public class AccountBalanceNotSufficientException extends Exception {
    public AccountBalanceNotSufficientException(String message) {
        super(message);
    }
}
