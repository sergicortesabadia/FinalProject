package com.Ironhack.FinalProject.accountmodels;

import com.Ironhack.FinalProject.enums.AccountStatus;
import com.Ironhack.FinalProject.embeddables.Money;
import com.Ironhack.FinalProject.usermodels.AccountHolder;
import net.bytebuddy.asm.Advice;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class CheckingAccount extends Account{
    @Embedded
    private final Money minimumBalance = new Money(BigDecimal.valueOf(250));

    private BigDecimal monthlyMaintenanceFee = new BigDecimal(0.01);
    private LocalDate lastMaintenanceDay = LocalDate.now();

    public CheckingAccount() {
    }

    public CheckingAccount(Money balance, AccountHolder primaryOwner, int secretKey) {
        super(balance, primaryOwner, secretKey);
    }

    public CheckingAccount(BigDecimal monthlyMaintenanceFee) {
        setMonthlyMaintenanceFee(monthlyMaintenanceFee);
    }

    public CheckingAccount(Money balance, AccountHolder owner, int secretKey, BigDecimal monthlyMaintenanceFee) {
        super(balance, owner, secretKey);;
        setMonthlyMaintenanceFee(monthlyMaintenanceFee);
    }

    public CheckingAccount(Money balance, AccountHolder owner, AccountHolder secondaryOwner, int secretKey, BigDecimal monthlyMaintenanceFee) {
        super(balance, owner, secondaryOwner, secretKey);
        setMonthlyMaintenanceFee(monthlyMaintenanceFee);
    }

    public Money getMinimumBalance() {
        return minimumBalance;
    }


    public BigDecimal getMonthlyMaintenanceFee() {
        return monthlyMaintenanceFee;
    }

    public void setMonthlyMaintenanceFee(BigDecimal monthlyMaintenanceFee) {
        this.monthlyMaintenanceFee = monthlyMaintenanceFee;
    }

    public LocalDate getLastMaintenanceDay() {
        return lastMaintenanceDay;
    }

    public void setLastMaintenanceDay(LocalDate lastMaintenanceDay) {
        this.lastMaintenanceDay = lastMaintenanceDay;
    }
}
