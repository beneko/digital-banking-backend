package com.digital.banking.mappers;

import com.digital.banking.dtos.CurrentBankAccountDTO;
import com.digital.banking.dtos.CustomerDTO;
import com.digital.banking.dtos.SavingBankAccountDTO;
import com.digital.banking.entities.CurrentAccount;
import com.digital.banking.entities.Customer;
import com.digital.banking.entities.SavingAccount;

public interface BankMapper {
    CustomerDTO fromCustomer(Customer customer);
    Customer toCustomer(CustomerDTO customerDTO);

    SavingBankAccountDTO fromSavingAccount(SavingAccount savingAccount);

    SavingAccount toSavingAccount(SavingBankAccountDTO savingBankAccountDTO);

    CurrentBankAccountDTO fromCurrentAccount(CurrentAccount currentAccount);

    CurrentAccount toCurrentAccount(CurrentBankAccountDTO currentBankAccountDTO);
}
