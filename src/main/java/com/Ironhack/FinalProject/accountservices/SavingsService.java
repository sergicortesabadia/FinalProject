package com.Ironhack.FinalProject.accountservices;

import com.Ironhack.FinalProject.accountmodels.Savings;
import com.Ironhack.FinalProject.accountservices.interfaces.SavingsServiceInterface;
import com.Ironhack.FinalProject.embeddables.Money;
import com.Ironhack.FinalProject.enums.AccountStatus;
import com.Ironhack.FinalProject.repositories.AccountRepository;
import com.Ironhack.FinalProject.repositories.SavingsRepository;
import com.Ironhack.FinalProject.usermodels.AccountHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class SavingsService implements SavingsServiceInterface {
    @Autowired
    SavingsRepository savingsRepository;
    @Autowired
    AccountRepository accountRepository;


    public Savings createSavings (AccountHolder accountHolder, BigDecimal initialBalance){
        Savings savings = new Savings();
        savings.setPrimaryOwner(accountHolder);
        if (initialBalance.compareTo(savings.getMinimumBalance().getAmount()) < 0) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Initial balance must be over "+savings.getMinimumBalance().getAmount());
        savings.setPrimaryOwner(accountHolder);
        savings.setSecretKey(0000);
        savings.setAccountStatus(AccountStatus.ACTIVE);
        savings.setBalance(new Money(initialBalance));
    //    savingsRepository.save(savings);
        return accountRepository.save(savings);
    }
    public Money addInterestRateOnSavings(Long accountNumber) {
        Savings savings = savingsRepository.findById(accountNumber).get();
        int dateDifference = LocalDate.now().EPOCH.getYear() - savings.getLastInterestDay().EPOCH.getYear();
        if (dateDifference >= 1) {
            BigDecimal one = new BigDecimal(1);
            for (int i = 0; i < dateDifference; i++) {
                Money interest = new Money(savings.getBalance().getAmount().multiply(one.add(savings.getInterestRate())));
                savings.setBalance(interest);
            }
            savings.setLastInterestDay(LocalDate.now());
            savingsRepository.save(savings);
        }
        return savings.getBalance();
    }

}
