package com.Ironhack.FinalProject.accountservices.interfaces;

import com.Ironhack.FinalProject.accountmodels.StudentCheckingAccount;
import com.Ironhack.FinalProject.usermodels.AccountHolder;

import java.math.BigDecimal;

public interface StudentCheckingAccountServiceInterface {
    StudentCheckingAccount createStudentCheckingAccount (AccountHolder accountHolder, BigDecimal initialBalance);
}
