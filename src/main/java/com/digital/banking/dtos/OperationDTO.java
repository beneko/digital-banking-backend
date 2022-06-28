package com.digital.banking.dtos;

import com.digital.banking.entities.BankAccount;
import com.digital.banking.enums.OperationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
public class OperationDTO {
    private Long id;
    private Date operationDate;
    private double amount;
    private String description;
    private OperationType type;
}
