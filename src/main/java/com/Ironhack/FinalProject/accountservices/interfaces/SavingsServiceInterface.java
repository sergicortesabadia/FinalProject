package com.Ironhack.FinalProject.accountservices.interfaces;

import com.Ironhack.FinalProject.accountmodels.Savings;
import com.Ironhack.FinalProject.embeddables.Money;
import com.Ironhack.FinalProject.usermodels.AccountHolder;

import java.math.BigDecimal;

public interface SavingsServiceInterface {
    Savings createSavings (AccountHolder accountHolder, BigDecimal initialBalance);
    Money addInterestRateOnSavings(Long accountNumber);
}
