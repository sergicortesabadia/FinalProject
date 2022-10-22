package com.Ironhack.FinalProject.accountservices;

import com.Ironhack.FinalProject.accountmodels.CheckingAccount;
import com.Ironhack.FinalProject.accountservices.interfaces.CheckingAccountServiceInterface;
import com.Ironhack.FinalProject.embeddables.Money;
import com.Ironhack.FinalProject.enums.AccountStatus;
import com.Ironhack.FinalProject.repositories.AccountRepository;
import com.Ironhack.FinalProject.repositories.CheckingAccountRepository;
import com.Ironhack.FinalProject.usermodels.AccountHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class CheckingAccountService implements CheckingAccountServiceInterface {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    CheckingAccountRepository checkingAccountRepository;



    public CheckingAccount createCheckingAccount(AccountHolder accountHolder, BigDecimal initialBalance) {
            CheckingAccount checkingAccount = new CheckingAccount();
            if (initialBalance.compareTo(checkingAccount.getMinimumBalance().getAmount()) < 0)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Initial balance must be over " + checkingAccount.getMinimumBalance().getAmount());
            checkingAccount.setSecretKey(0000);
            checkingAccount.setPrimaryOwner(accountHolder);
            checkingAccount.setAccountStatus(AccountStatus.ACTIVE);
            checkingAccount.setBalance(new Money(initialBalance));
         //   checkingAccountRepository.save(checkingAccount);
            return accountRepository.save(checkingAccount);
    }

    public Money payMonthlyFee(Long accountNumber) {
        CheckingAccount checkingAccount = checkingAccountRepository.findById(accountNumber).get();
        int dateDifference = ((LocalDate.now().EPOCH.getYear() - checkingAccount.getLastMaintenanceDay().EPOCH.getYear()) * 12) + (LocalDate.now().EPOCH.getMonthValue() - checkingAccount.getLastMaintenanceDay().EPOCH.getMonthValue());
        if (dateDifference >= 1) {
            BigDecimal one = new BigDecimal(1);
            for (int i = 0; i < dateDifference; i++) {
                Money maintenance = new Money(checkingAccount.getBalance().getAmount().multiply(one.subtract(checkingAccount.getMonthlyMaintenanceFee())));
                checkingAccount.setBalance(maintenance);
            }
            checkingAccount.setLastMaintenanceDay(LocalDate.now());
            checkingAccountRepository.save(checkingAccount);
        }
        return checkingAccount.getBalance();
    }
}
