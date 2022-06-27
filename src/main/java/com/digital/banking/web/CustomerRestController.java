package com.digital.banking.web;

import com.digital.banking.dtos.CustomerDTO;
import com.digital.banking.exceptions.CustomerNotFoundException;
import com.digital.banking.services.BankService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/v1")
public class CustomerRestController {
    private BankService bankService;

    @GetMapping("/customers")
    public List<CustomerDTO> customers(){
        return bankService.getCustomerList();
    }
    @GetMapping("/customers/{id}")
    public CustomerDTO getCustomer(@PathVariable(name = "id") Long customerId) throws CustomerNotFoundException {
        return bankService.getCustomer(customerId);
    }
}
