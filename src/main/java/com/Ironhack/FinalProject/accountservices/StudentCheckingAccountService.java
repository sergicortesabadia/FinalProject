package com.Ironhack.FinalProject.accountservices;

import com.Ironhack.FinalProject.accountmodels.StudentCheckingAccount;
import com.Ironhack.FinalProject.accountservices.interfaces.StudentCheckingAccountServiceInterface;
import com.Ironhack.FinalProject.embeddables.Money;
import com.Ironhack.FinalProject.enums.AccountStatus;
import com.Ironhack.FinalProject.repositories.AccountRepository;
import com.Ironhack.FinalProject.repositories.StudentCheckingAccountRepository;
import com.Ironhack.FinalProject.usermodels.AccountHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class StudentCheckingAccountService implements StudentCheckingAccountServiceInterface {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    StudentCheckingAccountRepository studentCheckingAccountRepository;

public StudentCheckingAccount createStudentCheckingAccount (AccountHolder accountHolder, BigDecimal initialBalance) {
    StudentCheckingAccount studentCheckingAccount = new StudentCheckingAccount();
    studentCheckingAccount.setPrimaryOwner(accountHolder);
    studentCheckingAccount.setAccountStatus(AccountStatus.ACTIVE);
    studentCheckingAccount.setSecretKey(0000);
    studentCheckingAccount.setBalance(new Money(initialBalance));
 //   studentCheckingAccountRepository.save(studentCheckingAccount);
    return accountRepository.save(studentCheckingAccount);
}
}
