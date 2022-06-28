package com.digital.banking.services;

import com.digital.banking.dtos.*;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public CustomerDTO updateCustomer(CustomerDTO customerDTO){
        log.info("Updating customer");
        Customer updatedCustomer = customerRepository.save(bankMapper.toCustomer(customerDTO));
        return bankMapper.fromCustomer(updatedCustomer);
    }

    @Override
    public void deleteCustomer(Long customerId){
        log.info("Deleting customer");
        customerRepository.deleteById(customerId);
    }

    @Override
    public CurrentBankAccountDTO saveCurrentAccount(Long customerId, double overDraft, double initialBalance) throws CustomerNotFoundException{
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
        return bankMapper.fromCurrentAccount(bankAccountRepository.save(currentAccount));
    }

    @Override
    public SavingBankAccountDTO saveSavingAccount(Long customerId, double interestRate, double initialBalance) throws CustomerNotFoundException{
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
        return bankMapper.fromSavingAccount(bankAccountRepository.save(savingAccount));
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
    public List<BankAccountDTO> getBankAccountList() {
        return bankAccountRepository.findAll().stream()
                .map(account -> {
            if(account instanceof SavingAccount){
                SavingAccount savingAccount = (SavingAccount) account;
                return bankMapper.fromSavingAccount(savingAccount);
            } else {
                CurrentAccount currentAccount = (CurrentAccount) account;
                return bankMapper.fromCurrentAccount((currentAccount));
            }
        })
                .collect(Collectors.toList());
    }

    @Override
    public BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException{
        BankAccount bankAccount = bankAccountRepository.findById(accountId).orElseThrow(()-> new BankAccountNotFoundException("Bank Account not Found!"));
        if(bankAccount instanceof CurrentAccount){
            return bankMapper.fromCurrentAccount((CurrentAccount) bankAccount);
        } else {
            return bankMapper.fromSavingAccount(((SavingAccount) bankAccount));
        }
    }

    @Override
    public List<OperationDTO> getOperationList(String accountId){
        return operationRepository.findByBankAccountId(accountId).stream()
                .map(operation -> bankMapper.fromOperation(operation))
                .collect(Collectors.toList());
    }

    @Override
    public AccountHistoryDTO getAccountHistory(String accountId, int offset, int limit) throws BankAccountNotFoundException{
        BankAccount bankAccount = bankAccountRepository.findById(accountId).orElseThrow(() -> new BankAccountNotFoundException("Bank Account not Found!"));
        Page<Operation> operationPage = operationRepository.findByBankAccountId(accountId, PageRequest.of(offset, limit));
        List<OperationDTO> operationDTOList = operationPage.getContent()
                .stream()
                .map(operation -> bankMapper.fromOperation(operation))
                .collect(Collectors.toList());
        AccountHistoryDTO accountHistoryDTO = new AccountHistoryDTO();
        accountHistoryDTO.setAccountId(bankAccount.getId());
        accountHistoryDTO.setBalance(bankAccount.getBalance());
        accountHistoryDTO.setOffset(offset);
        accountHistoryDTO.setLimit(limit);
        accountHistoryDTO.setTotalPages(operationPage.getTotalPages());
        accountHistoryDTO.setOperationDTOs(operationDTOList);
        return accountHistoryDTO;
    }

    @Override
    public void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, AccountBalanceNotSufficientException{
        BankAccount bankAccount = bankAccountRepository.findById(accountId).orElseThrow(()-> new BankAccountNotFoundException("Bank Account not Found!"));
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
        BankAccount bankAccount = bankAccountRepository.findById(accountId).orElseThrow(()-> new BankAccountNotFoundException("Bank Account not Found!"));
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
