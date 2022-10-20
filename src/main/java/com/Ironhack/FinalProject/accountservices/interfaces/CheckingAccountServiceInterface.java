package com.Ironhack.FinalProject.accountservices.interfaces;

import com.Ironhack.FinalProject.accountmodels.CheckingAccount;
import com.Ironhack.FinalProject.embeddables.Money;
import com.Ironhack.FinalProject.usermodels.AccountHolder;

import java.math.BigDecimal;

public interface CheckingAccountServiceInterface {
    CheckingAccount createCheckingAccount(AccountHolder accountHolder, BigDecimal initialBalance);
    Money payMonthlyFee(Long accountNumber);
}
