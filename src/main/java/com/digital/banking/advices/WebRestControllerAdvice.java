package com.digital.banking.advices;

import com.digital.banking.exceptions.AccountBalanceNotSufficientException;
import com.digital.banking.exceptions.BankAccountNotFoundException;
import com.digital.banking.exceptions.CustomerNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;

@RestControllerAdvice
public class WebRestControllerAdvice {

    @ExceptionHandler({BankAccountNotFoundException.class, CustomerNotFoundException.class, AccountNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String resourceNotFoundExceptionHandler(Exception ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(AccountBalanceNotSufficientException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String accountBalanceNotSufficientExceptionHandler(AccountBalanceNotSufficientException ex){
        return ex.getMessage();
    }
}
