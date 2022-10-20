package com.Ironhack.FinalProject.accountmodels;

import com.Ironhack.FinalProject.enums.AccountStatus;
import com.Ironhack.FinalProject.embeddables.Money;
import com.Ironhack.FinalProject.usermodels.AccountHolder;

import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
public class StudentCheckingAccount extends Account{

    public StudentCheckingAccount() {
    }

    public StudentCheckingAccount(Money balance, AccountHolder owner, int secretKey) {
        super(balance, owner, secretKey);
    }

    public StudentCheckingAccount(Money balance, AccountHolder owner, AccountHolder secondaryOwner, int secretKey) {
        super(balance, owner, secondaryOwner, secretKey);
    }
}
