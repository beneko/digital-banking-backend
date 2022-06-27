package com.digital.banking;

import com.digital.banking.dtos.CustomerDTO;
import com.digital.banking.exceptions.AccountBalanceNotSufficientException;
import com.digital.banking.exceptions.BankAccountNotFoundException;
import com.digital.banking.exceptions.CustomerNotFoundException;
import com.digital.banking.services.BankService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.stream.Stream;

@SpringBootApplication
public class DigitalBankingBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(DigitalBankingBackendApplication.class, args);
	}

	@Bean
	CommandLineRunner start(BankService bankService){
		return args -> {
			// create some customer
			Stream.of("Jack", "Daniel", "Sam", "Bob").forEach(name->{
				CustomerDTO customerDTO = new CustomerDTO();
				customerDTO.setName(name);
				customerDTO.setEmail(name.toLowerCase() + "@gmail.com");
				bankService.saveCustomer(customerDTO);
			});
			// create a current account and a saving account for every customer
			bankService.getCustomerList().forEach(customer -> {
				try {
					bankService.saveCurrentAccount(customer.getId(), 5000, Math.random()*1000000);
					bankService.saveSavingAccount(customer.getId(), 5.5, Math.random()*5000000);
				} catch (CustomerNotFoundException e) {
					e.printStackTrace();
				}
			});
			// add 10 credit and 10 debit operation for every bank account
			bankService.getBankAccountList().forEach(bankAccount -> {
				for (int i=0 ; i<10 ; i++){
					try {
						bankService.debit(bankAccount.getId(), 1000 + Math.random()*10000, "debit");
						bankService.credit(bankAccount.getId(), 1000 + Math.random()*10000, "credit");
					} catch (BankAccountNotFoundException | AccountBalanceNotSufficientException e) {
						throw new RuntimeException(e);
					}
				}
			});
		};
	}
}
