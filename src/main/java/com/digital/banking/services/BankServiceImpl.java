package com.digital.banking.services;

import com.digital.banking.entities.BankAccount;
import com.digital.banking.entities.CurrentAccount;
import com.digital.banking.entities.Customer;
import com.digital.banking.entities.SavingAccount;
import com.digital.banking.repositories.BankAccountRepository;
import com.digital.banking.repositories.CustomerRepository;
import com.digital.banking.repositories.OperationRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
// The same as private Logger logger = LoggerFactory.getLogger(this.getClass().getName());
@Slf4j
public class BankServiceImpl implements BankService {

    private CustomerRepository customerRepository;
    private BankAccountRepository bankAccountRepository;
    private OperationRepository operationRepository;

    //private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Override
    public Customer saveCustomer(Customer customer) {
        return null;
    }

    @Override
    public CurrentAccount saveCurrentAccount(Long customerId, double overDraft, double initialBalance) {
        return null;
    }

    @Override
    public SavingAccount saveSavingAccount(Long customerId, double overDraft, double interestRate) {
        return null;
    }


    @Override
    public List<Customer> getCustomerList() {
        return null;
    }

    @Override
    public BankAccount getBankAccount(String accountId) {
        return null;
    }

    @Override
    public void debit(String accountId, double amount, String description) {

    }

    @Override
    public void credit(String accountId, double amount, String description) {

    }

    @Override
    public void transfer(String accountIdSource, String accountIdDestination, double amount) {

    }
}
