package com.digital.banking;

import com.digital.banking.entities.CurrentAccount;
import com.digital.banking.entities.Customer;
import com.digital.banking.entities.SavingAccount;
import com.digital.banking.enums.AccountStatus;
import com.digital.banking.repositories.BankAccountRepository;
import com.digital.banking.repositories.CustomerRepository;
import com.digital.banking.repositories.OperationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class DigitalBankingBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(DigitalBankingBackendApplication.class, args);
	}

	@Bean
	CommandLineRunner start(CustomerRepository customerRepository,
							BankAccountRepository bankAccountRepository,
							OperationRepository operationRepository){
		return args -> {
			Stream.of("Jack", "Daniel", "Sam", "Bob").forEach(name->{
				Customer customer = new Customer();
				customer.setName(name);
				customer.setEmail(name.toLowerCase() + "gmail.com");
				customerRepository.save(customer);
			});
			customerRepository.findAll().forEach(customer -> {
				CurrentAccount currentAccount = new CurrentAccount();
				currentAccount.setId(UUID.randomUUID().toString());
				currentAccount.setBalance(Math.random()*1000000);
				currentAccount.setCreatedAt(new Date());
				currentAccount.setOverDraft(5000);
				currentAccount.setStatus(AccountStatus.CREATED);
				currentAccount.setCustomer(customer);
				bankAccountRepository.save(currentAccount);
			});
			customerRepository.findAll().forEach(customer -> {
				SavingAccount savingAccount = new SavingAccount();
				savingAccount.setId(UUID.randomUUID().toString());
				savingAccount.setBalance(Math.random()*1000000);
				savingAccount.setCreatedAt(new Date());
				savingAccount.setInterestRate(3);
				savingAccount.setStatus(AccountStatus.CREATED);
				savingAccount.setCustomer(customer);
				bankAccountRepository.save(savingAccount);
			});
		};
	}
}
