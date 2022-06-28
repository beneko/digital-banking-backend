package com.digital.banking.repositories;

import com.digital.banking.entities.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OperationRepository extends JpaRepository<Operation, Long> {
    List<Operation> findByBankAccountId(String bankAccountId);
    Page<Operation> findByBankAccountId(String bankAccountId, Pageable pageable);
}
