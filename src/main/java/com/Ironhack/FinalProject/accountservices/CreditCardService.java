package com.Ironhack.FinalProject.accountservices;

import com.Ironhack.FinalProject.accountmodels.CreditCard;
import com.Ironhack.FinalProject.accountservices.interfaces.CreditCardServiceInterface;
import com.Ironhack.FinalProject.embeddables.Money;
import com.Ironhack.FinalProject.enums.AccountStatus;
import com.Ironhack.FinalProject.repositories.AccountRepository;
import com.Ironhack.FinalProject.repositories.CreditCardRepository;
import com.Ironhack.FinalProject.usermodels.AccountHolder;
import com.Ironhack.FinalProject.userservices.interfaces.AdminServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class CreditCardService implements CreditCardServiceInterface {
    @Autowired
    CreditCardRepository creditCardRepository;
    @Autowired
    AccountRepository accountRepository;

    public CreditCard createCreditCard(AccountHolder accountHolder, BigDecimal initialBalance) {
        CreditCard creditCard = new CreditCard();
        creditCard.setPrimaryOwner(accountHolder);
        creditCard.setSecretKey(0000);
        creditCard.setAccountStatus(AccountStatus.ACTIVE);
        creditCard.setBalance(new Money(initialBalance));
        creditCard.setLastInterestDay(LocalDate.now());
//    creditCardRepository.save(creditCard);
        return accountRepository.save(creditCard);
    }

    public Money addInterestRateOnCreditCard(Long accountNumber) {
        CreditCard creditCard = creditCardRepository.findById(accountNumber).get();
        int dateDifference = ((LocalDate.now().EPOCH.getYear() - creditCard.getLastInterestDay().EPOCH.getYear()) * 12) + (LocalDate.now().EPOCH.getMonthValue() - creditCard.getLastInterestDay().EPOCH.getMonthValue());
        if (dateDifference >= 1) {
            BigDecimal one = new BigDecimal(1);
            BigDecimal twelve = new BigDecimal(12);
            for (int i = 0; i < dateDifference; i++) {
                Money interest = new Money(creditCard.getBalance().getAmount().multiply(one.subtract(creditCard.getInterestRate().divide(twelve))));
                creditCard.setBalance(interest);
            }
            creditCard.setLastInterestDay(LocalDate.now());
            creditCardRepository.save(creditCard);
        }
        return creditCard.getBalance();
    }
}
