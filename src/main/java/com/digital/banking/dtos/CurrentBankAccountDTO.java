package com.digital.banking.dtos;

import com.digital.banking.enums.AccountStatus;

import lombok.Data;
import java.util.Date;

@Data
public abstract class CurrentBankAccountDTO {
    private String id;
    private double balance;
    private Date createdAt;
    private AccountStatus status;
    private CustomerDTO customerDTO;
    private double overDraft;
}
