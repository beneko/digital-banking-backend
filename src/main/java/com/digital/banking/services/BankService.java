package com.digital.banking.services;

import com.digital.banking.entities.BankAccount;
import com.digital.banking.entities.CurrentAccount;
import com.digital.banking.entities.Customer;
import com.digital.banking.entities.SavingAccount;

import java.util.List;

public interface BankService {
    Customer saveCustomer(Customer customer);
    CurrentAccount saveCurrentAccount(Long customerId, double overDraft, double initialBalance);
    SavingAccount saveSavingAccount(Long customerId, double interestRate, double initialBalance);

    List<Customer> getCustomerList();
    BankAccount getBankAccount(String accountId);
    void debit(String accountId, double amount, String description);
    void credit(String accountId, double amount, String description);
    void transfer(String accountIdSource, String accountIdDestination, double amount);
}