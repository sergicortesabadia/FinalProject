package com.Ironhack.FinalProject.userservices;

import com.Ironhack.FinalProject.accountmodels.Account;
import com.Ironhack.FinalProject.accountmodels.CheckingAccount;
import com.Ironhack.FinalProject.accountmodels.Savings;
import com.Ironhack.FinalProject.accountservices.interfaces.CheckingAccountServiceInterface;
import com.Ironhack.FinalProject.accountservices.interfaces.CreditCardServiceInterface;
import com.Ironhack.FinalProject.accountservices.interfaces.SavingsServiceInterface;
import com.Ironhack.FinalProject.embeddables.Money;
import com.Ironhack.FinalProject.repositories.*;
import com.Ironhack.FinalProject.transactions.Transaction;
import com.Ironhack.FinalProject.transactions.transactionservice.transactionserviceinterface.TransactionServiceInterface;
import com.Ironhack.FinalProject.usermodels.ThirdParty;
import com.Ironhack.FinalProject.userservices.interfaces.AccountHolderServiceInterface;
import com.Ironhack.FinalProject.userservices.interfaces.ThirdPartyServiceInterface;
import org.hibernate.annotations.Check;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

@Service
public class ThirdPartyService implements ThirdPartyServiceInterface {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    CheckingAccountRepository checkingAccountRepository;
    @Autowired
    SavingsRepository savingsRepository;
    @Autowired
    ThirdPartyRepository thirdPartyRepository;

    public Account thirdPartyAddBalance(String hashedKey, BigDecimal amount, Long accountId){
        if (!thirdPartyRepository.findByHashedKey(hashedKey).isPresent()) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        Account account = accountRepository.findById(accountId).get();
        account.getBalance().increaseAmount(amount);
        return accountRepository.save(account);
    }
    public Account thirdPartySubtractBalance(String hashedKey, BigDecimal amount, Long accountId){
        if (!thirdPartyRepository.findByHashedKey(hashedKey).isPresent()) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        BigDecimal zero = BigDecimal.valueOf(0);
        if(savingsRepository.existsById(accountId)){
            Savings savings = savingsRepository.findById(accountId).get();
            savings.getBalance().decreaseAmount(amount);
            if(savings.getBalance().getAmount().compareTo(zero)==-1)throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not enough funds");
            if(savings.getBalance().getAmount().compareTo(savings.getMinimumBalance().getAmount())==-1){
                savings.getBalance().decreaseAmount(savings.getPenaltyFee());
            }
            return savingsRepository.save(savings);
        } else if (checkingAccountRepository.existsById(accountId)){
            CheckingAccount checkingAccount = checkingAccountRepository.findById(accountId).get();
            checkingAccount.getBalance().decreaseAmount(amount);
            if(checkingAccount.getBalance().getAmount().compareTo(zero)==-1)throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not enough funds");
            if(checkingAccount.getBalance().getAmount().compareTo(checkingAccount.getMinimumBalance().getAmount())==-1){
                checkingAccount.getBalance().decreaseAmount(checkingAccount.getPenaltyFee());
            }
            return checkingAccountRepository.save(checkingAccount);
        } else {
            Account account = accountRepository.findById(accountId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));
            account.getBalance().decreaseAmount(amount);
            if(account.getBalance().getAmount().compareTo(zero)==-1)throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not enough funds");
            return accountRepository.save(account);
        }
    }
}
