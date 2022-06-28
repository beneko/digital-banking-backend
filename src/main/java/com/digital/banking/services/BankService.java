package com.digital.banking.services;

import com.digital.banking.dtos.*;
import com.digital.banking.exceptions.AccountBalanceNotSufficientException;
import com.digital.banking.exceptions.BankAccountNotFoundException;
import com.digital.banking.exceptions.CustomerNotFoundException;

import java.util.List;

public interface BankService {
    CustomerDTO saveCustomer(CustomerDTO customerDTO);

    void deleteCustomer(Long customerId) throws CustomerNotFoundException;

    CurrentBankAccountDTO saveCurrentAccount(Long customerId, double overDraft, double initialBalance) throws CustomerNotFoundException;
    SavingBankAccountDTO saveSavingAccount(Long customerId, double interestRate, double initialBalance) throws CustomerNotFoundException;

    List<CustomerDTO> getCustomerList();

    CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException;

    CustomerDTO updateCustomer(CustomerDTO customerDTO) throws CustomerNotFoundException;

    List<BankAccountDTO> getBankAccountList();
    BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException;

    List<OperationDTO> getOperationList(String accountId) throws BankAccountNotFoundException;

    OperationDTO debit(String accountId, double amount, String description) throws BankAccountNotFoundException, AccountBalanceNotSufficientException;
    void credit(String accountId, double amount, String description) throws BankAccountNotFoundException, AccountBalanceNotSufficientException;
    OperationDTO transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, AccountBalanceNotSufficientException;

    AccountHistoryDTO getAccountHistory(String accountId, int offset, int limit) throws BankAccountNotFoundException;
}
