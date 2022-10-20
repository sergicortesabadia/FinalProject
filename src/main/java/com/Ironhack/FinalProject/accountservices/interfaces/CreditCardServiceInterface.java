package com.Ironhack.FinalProject.accountservices.interfaces;

import com.Ironhack.FinalProject.accountmodels.CreditCard;
import com.Ironhack.FinalProject.embeddables.Money;
import com.Ironhack.FinalProject.usermodels.AccountHolder;

import java.math.BigDecimal;

public interface CreditCardServiceInterface {
    CreditCard createCreditCard (AccountHolder accountHolder, BigDecimal initialBalance);
    Money addInterestRateOnCreditCard(Long accountNumber);
}
