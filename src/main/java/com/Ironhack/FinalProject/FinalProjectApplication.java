package com.Ironhack.FinalProject;

import com.Ironhack.FinalProject.accountmodels.CheckingAccount;
import com.Ironhack.FinalProject.accountmodels.CreditCard;
import com.Ironhack.FinalProject.accountmodels.Savings;
import com.Ironhack.FinalProject.accountmodels.StudentCheckingAccount;
import com.Ironhack.FinalProject.embeddables.Address;
import com.Ironhack.FinalProject.embeddables.Money;
import com.Ironhack.FinalProject.repositories.*;
import com.Ironhack.FinalProject.usermodels.AccountHolder;
import com.Ironhack.FinalProject.usermodels.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootApplication
public class FinalProjectApplication implements CommandLineRunner {
	@Autowired
	AccountRepository accountRepository;
	@Autowired
	AccountHolderRepository accountHolderRepository;
	@Autowired
	AdminRepository adminRepository;
	@Autowired
	CheckingAccountRepository checkingAccountRepository;
	@Autowired
	CreditCardRepository creditCardRepository;
	@Autowired
	SavingsRepository savingsRepository;
	@Autowired
	StudentCheckingAccountRepository studentCheckingAccountRepository;
	@Autowired
	ThirdPartyRepository thirdPartyRepository;
	@Autowired
	UserRepository userRepository;


	public static void main(String[] args) {
		SpringApplication.run(FinalProjectApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception{

	}
}

