package com.digital.banking.exceptions;

public class AccountBalanceNotSufficientException extends Exception {
    public AccountBalanceNotSufficientException() {
        super("Account balance is not sufficient!");
    }
}
