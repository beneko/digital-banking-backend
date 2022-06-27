package com.digital.banking.mappers;

import com.digital.banking.dtos.CustomerDTO;
import com.digital.banking.entities.Customer;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

//We can also use MapStruct
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
}
