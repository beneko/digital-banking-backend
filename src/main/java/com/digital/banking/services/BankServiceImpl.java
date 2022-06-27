package com.digital.banking.services;

import com.digital.banking.dtos.CustomerDTO;
import com.digital.banking.entities.*;
import com.digital.banking.enums.AccountStatus;
import com.digital.banking.enums.OperationType;
import com.digital.banking.exceptions.AccountBalanceNotSufficientException;
import com.digital.banking.exceptions.BankAccountNotFoundException;
import com.digital.banking.exceptions.CustomerNotFoundException;
import com.digital.banking.mappers.BankMapper;
import com.digital.banking.repositories.BankAccountRepository;
import com.digital.banking.repositories.CustomerRepository;
import com.digital.banking.repositories.OperationRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
// The same as private Logger logger = LoggerFactory.getLogger(this.getClass().getName());
@Slf4j
public class BankServiceImpl implements BankService {

    private CustomerRepository customerRepository;
    private BankAccountRepository bankAccountRepository;
    private OperationRepository operationRepository;

    private BankMapper bankMapper;

    //private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        log.info("Saving new Customer");
        Customer savedCustomer = customerRepository.save(bankMapper.toCustomer(customerDTO));
        return bankMapper.fromCustomer(savedCustomer);
    }

    @Override
    public CurrentAccount saveCurrentAccount(Long customerId, double overDraft, double initialBalance) throws CustomerNotFoundException{
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if(customer==null){
            throw new CustomerNotFoundException("Customer not found!");
        }
        CurrentAccount currentAccount = new CurrentAccount();
        currentAccount.setId(UUID.randomUUID().toString());
        currentAccount.setCustomer(customer);
        currentAccount.setOverDraft(overDraft);
        currentAccount.setBalance(initialBalance);
        currentAccount.setCreatedAt(new Date());
        currentAccount.setStatus(AccountStatus.CREATED);
        return bankAccountRepository.save(currentAccount);
    }

    @Override
    public SavingAccount saveSavingAccount(Long customerId, double interestRate, double initialBalance) throws CustomerNotFoundException{
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if(customer==null){
            throw new CustomerNotFoundException("Customer not found!");
        }
        SavingAccount savingAccount = new SavingAccount();
        savingAccount.setId(UUID.randomUUID().toString());
        savingAccount.setCustomer(customer);
        savingAccount.setInterestRate(interestRate);
        savingAccount.setBalance(initialBalance);
        savingAccount.setCreatedAt(new Date());
        savingAccount.setStatus(AccountStatus.CREATED);
        return bankAccountRepository.save(savingAccount);
    }


    @Override
    public List<CustomerDTO> getCustomerList() {

        /* imperative programming
        List<CustomerDTO> customerDTOS = new ArrayList<>();
        customerRepository.findAll().forEach(customer -> {
            customerDTOS.add(bankMapper.fromCustomer(customer));
        });
         */

        // functional programming
        return customerRepository.findAll().stream()
                .map(customer -> bankMapper.fromCustomer(customer))
                .collect(Collectors.toList());
    }
    @Override
    public CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId).orElseThrow(()-> new CustomerNotFoundException("Customer not found!"));
        return bankMapper.fromCustomer(customer);
    }

    @Override
    public List<BankAccount> getBankAccountList() {
        return bankAccountRepository.findAll();
    }

    @Override
    public BankAccount getBankAccount(String accountId) throws BankAccountNotFoundException{
        return bankAccountRepository.findById(accountId).orElseThrow(()-> new BankAccountNotFoundException("Bank Account not Found!"));
    }

    @Override
    public void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, AccountBalanceNotSufficientException{
        BankAccount bankAccount = getBankAccount(accountId);
        if(bankAccount.getBalance() < amount )
            throw new AccountBalanceNotSufficientException("Account balance is not sufficient!");
        Operation operation = new Operation();
        operation.setType(OperationType.DEBIT);
        operation.setAmount(amount);
        operation.setDescription(description);
        operation.setOperationDate(new Date());
        operation.setBankAccount(bankAccount);
        operationRepository.save(operation);
        bankAccount.setBalance(bankAccount.getBalance() - amount);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void credit(String accountId, double amount, String description) throws BankAccountNotFoundException{
        BankAccount bankAccount = getBankAccount(accountId);
        Operation operation = new Operation();
        operation.setType(OperationType.CREDIT);
        operation.setAmount(amount);
        operation.setDescription(description);
        operation.setOperationDate(new Date());
        operation.setBankAccount(bankAccount);
        operationRepository.save(operation);
        bankAccount.setBalance(bankAccount.getBalance() + amount);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, AccountBalanceNotSufficientException {
        debit(accountIdSource, amount,"Transfer to " + accountIdDestination);
        credit(accountIdDestination, amount,"Transfer from " + accountIdSource);
    }
}
