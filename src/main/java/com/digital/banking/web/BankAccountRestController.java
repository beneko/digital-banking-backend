package com.digital.banking.web;

import com.digital.banking.entities.BankAccount;
import com.digital.banking.services.BankService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class BankAccountRestController {
    private BankService bankService;

}
