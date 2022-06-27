package com.digital.banking.mappers;

import com.digital.banking.dtos.CustomerDTO;
import com.digital.banking.entities.Customer;

public interface BankMapper {
    CustomerDTO fromCustomer(Customer customer);
    Customer fromCustomerDTO(CustomerDTO customerDTO);
}
