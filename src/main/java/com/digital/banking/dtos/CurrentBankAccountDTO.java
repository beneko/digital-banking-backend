package com.digital.banking.dtos;

import com.digital.banking.enums.AccountStatus;

import lombok.Data;
import java.util.Date;

@Data
public class CurrentBankAccountDTO extends BankAccountDTO{
    private double overDraft;
}
