package com.digital.banking.dtos;

import lombok.Data;

import java.util.List;

@Data
public class AccountHistoryDTO {
    private String accountId;
    private double balance;
    private int offset;
    private int totalPages;
    private int limit;
    private List<OperationDTO> OperationDTOs;
}
