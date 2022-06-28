package com.digital.banking.mappers;

import com.digital.banking.dtos.CurrentBankAccountDTO;
import com.digital.banking.dtos.CustomerDTO;
import com.digital.banking.dtos.SavingBankAccountDTO;
import com.digital.banking.entities.CurrentAccount;
import com.digital.banking.entities.Customer;
import com.digital.banking.entities.SavingAccount;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

//We can also use MapStruct for Data Object Mapping but for now we do it manually
@Service
public class BankMapperImpl implements BankMapper{
    @Override
    public CustomerDTO fromCustomer(Customer customer){
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);
        return customerDTO;
    }
    @Override
    public Customer toCustomer(CustomerDTO customerDTO){
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        return customer;
    }
    @Override
    public SavingBankAccountDTO fromSavingAccount(SavingAccount savingAccount){
        SavingBankAccountDTO savingBankAccountDTO = new SavingBankAccountDTO();
        BeanUtils.copyProperties(savingAccount, savingBankAccountDTO);
        // manually transfer Customer to savingBankAccountDTO
        savingBankAccountDTO.setCustomerDTO(fromCustomer(savingAccount.getCustomer()));
        savingBankAccountDTO.setType(savingAccount.getClass().getSimpleName());
        return savingBankAccountDTO;
    }
    @Override
    public SavingAccount toSavingAccount(SavingBankAccountDTO savingBankAccountDTO){
        SavingAccount savingAccount = new SavingAccount();
        BeanUtils.copyProperties(savingBankAccountDTO, savingAccount);
        // manually transfer CustomerDTO to savingAccount
        savingAccount.setCustomer(toCustomer(savingBankAccountDTO.getCustomerDTO()));
        return savingAccount;
    }
    @Override
    public CurrentBankAccountDTO fromCurrentAccount(CurrentAccount currentAccount){
        CurrentBankAccountDTO currentBankAccountDTO = new CurrentBankAccountDTO();
        BeanUtils.copyProperties(currentAccount, currentBankAccountDTO);
        // manually transfer Customer to currentBankAccountDTO
        currentBankAccountDTO.setCustomerDTO(fromCustomer(currentAccount.getCustomer()));
        currentBankAccountDTO.setType(currentAccount.getClass().getSimpleName());
        return currentBankAccountDTO;

    }

    @Override
    public CurrentAccount toCurrentAccount(CurrentBankAccountDTO currentBankAccountDTO){
        CurrentAccount currentAccount = new CurrentAccount();
        BeanUtils.copyProperties(currentBankAccountDTO, currentAccount);
        // manually transfer CustomerDTO to currentAccount
        currentAccount.setCustomer(toCustomer(currentBankAccountDTO.getCustomerDTO()));
        return currentAccount;
    }
}
