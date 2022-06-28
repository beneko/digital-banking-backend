package com.digital.banking.exceptions;

public class BankAccountNotFoundException extends Exception{
    public BankAccountNotFoundException() {
        super("Bank account not found!");
    }
}
