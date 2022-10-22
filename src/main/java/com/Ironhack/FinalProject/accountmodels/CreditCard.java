package com.Ironhack.FinalProject.accountmodels;


import com.Ironhack.FinalProject.embeddables.Money;
import com.Ironhack.FinalProject.usermodels.AccountHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class CreditCard extends Account{
    @Embedded
    private Money creditLimit = new Money(java.math.BigDecimal.valueOf(100));

    private java.math.BigDecimal interestRate = java.math.BigDecimal.valueOf(0.2);
    private LocalDate lastInterestDay = LocalDate.now();

    public CreditCard() {
    }

    public CreditCard(Money balance, AccountHolder primaryOwner, int secretKey) {
        super(balance, primaryOwner, secretKey);
    }

    public CreditCard(Money balance, AccountHolder owner, int secretKey, Money creditLimit, java.math.BigDecimal interestRate) {
        super(balance, owner, secretKey);
        setCreditLimit(creditLimit);
        setInterestRate(interestRate);
    }

    public CreditCard(Money balance, AccountHolder owner, AccountHolder secondaryOwner, int secretKey, Money creditLimit, java.math.BigDecimal interestRate) {
        super(balance, owner, secondaryOwner, secretKey);
        setCreditLimit(creditLimit);
        setInterestRate(interestRate);
    }

    public Money getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(Money creditLimit) {
        if(creditLimit.getAmount().compareTo(BigDecimal.valueOf(100000))==1 || creditLimit.getAmount().compareTo(BigDecimal.valueOf(100))==-1) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Minimum balance can not be greater than 1000 or smaller than 100");
        } else{
            this.creditLimit = creditLimit;
        }
    }

    public java.math.BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(java.math.BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public LocalDate getLastInterestDay() {
        return lastInterestDay;
    }

    public void setLastInterestDay(LocalDate lastInterestDay) {
        this.lastInterestDay = lastInterestDay;
    }
}
