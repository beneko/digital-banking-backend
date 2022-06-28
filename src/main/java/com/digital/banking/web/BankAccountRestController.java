package com.digital.banking.web;

import com.digital.banking.dtos.AccountHistoryDTO;
import com.digital.banking.dtos.BankAccountDTO;
import com.digital.banking.dtos.OperationDTO;
import com.digital.banking.exceptions.BankAccountNotFoundException;
import com.digital.banking.services.BankService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/accounts/{accountId}/operations")
    public List<OperationDTO> getAccountOperationHistory(@PathVariable String accountId){
        return bankService.getOperationList(accountId);
    }
    @GetMapping("/accounts/{accountId}/pageOperation")
    public AccountHistoryDTO getAccountHistory(@PathVariable String accountId,
                                               @RequestParam(name = "offset", defaultValue = "0") int offset,
                                               @RequestParam(name = "limit", defaultValue = "10") int limit) throws BankAccountNotFoundException {
        return bankService.getAccountHistory(accountId, offset, limit);
    }
}
