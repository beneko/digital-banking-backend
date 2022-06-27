package com.digital.banking.web;

import com.digital.banking.dtos.BankAccountDTO;
import com.digital.banking.entities.BankAccount;
import com.digital.banking.exceptions.BankAccountNotFoundException;
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
public class BankAccountRestController {
    private BankService bankService;

    @GetMapping("/accounts")
    public List<BankAccountDTO> getBankAccountList(){
        return bankService.getBankAccountList();
    }
    @GetMapping("/accounts/{accountId}")
    public BankAccountDTO getBankAccount(@PathVariable String accountId) throws BankAccountNotFoundException {
        return bankService.getBankAccount(accountId);
    }
}
