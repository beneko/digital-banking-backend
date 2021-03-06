package com.digital.banking.web;

import com.digital.banking.dtos.CustomerDTO;
import com.digital.banking.exceptions.CustomerNotFoundException;
import com.digital.banking.services.BankService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/v1")
@CrossOrigin("*")
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
    @GetMapping("/customers/search")
    public List<CustomerDTO> searchCustomers(@RequestParam(name= "keyword", defaultValue = "") String keyword){
        return bankService.searchCustomers(keyword);
    }
    @PostMapping("/customers")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        return bankService.saveCustomer(customerDTO);
    }
    @PutMapping("/customers/{customerId}")
    public CustomerDTO updateCustomers(@PathVariable Long customerId, @RequestParam CustomerDTO customerDTO) throws CustomerNotFoundException {
        customerDTO.setId(customerId);
        return bankService.updateCustomer(customerDTO);
    }
    @DeleteMapping("/customers/{id}")
    public void deleteCustomer(@PathVariable Long id) throws CustomerNotFoundException {
        bankService.deleteCustomer(id);
    }
}
