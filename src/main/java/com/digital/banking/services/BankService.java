package com.digital.banking.services;

import com.digital.banking.dtos.BankAccountDTO;
import com.digital.banking.dtos.CurrentBankAccountDTO;
import com.digital.banking.dtos.CustomerDTO;
import com.digital.banking.dtos.SavingBankAccountDTO;
import com.digital.banking.entities.BankAccount;
import com.digital.banking.exceptions.AccountBalanceNotSufficientException;
import com.digital.banking.exceptions.BankAccountNotFoundException;
import com.digital.banking.exceptions.CustomerNotFoundException;

import java.util.List;

public interface BankService {
    CustomerDTO saveCustomer(CustomerDTO customerDTO);

    void deleteCustomer(Long customerId);

    CurrentBankAccountDTO saveCurrentAccount(Long customerId, double overDraft, double initialBalance) throws CustomerNotFoundException;
    SavingBankAccountDTO saveSavingAccount(Long customerId, double interestRate, double initialBalance) throws CustomerNotFoundException;

    List<CustomerDTO> getCustomerList();

    CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException;

    CustomerDTO updateCustomer(CustomerDTO customerDTO);

    List<BankAccountDTO> getBankAccountList();
    BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException;
    void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, AccountBalanceNotSufficientException;
    void credit(String accountId, double amount, String description) throws BankAccountNotFoundException, AccountBalanceNotSufficientException;
    void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, AccountBalanceNotSufficientException;
}
