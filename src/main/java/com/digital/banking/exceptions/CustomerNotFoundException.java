package com.digital.banking.exceptions;

public class CustomerNotFoundException extends Exception{
    public CustomerNotFoundException(){
        super("Customer not found!");
    }
}
