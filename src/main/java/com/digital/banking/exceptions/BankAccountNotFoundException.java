package com.digital.banking.exceptions;

public class BankAccountNotFoundException extends Exception{
    public BankAccountNotFoundException(String message) {
        super(message);
    }
}
