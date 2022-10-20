package com.Ironhack.FinalProject.accountmodels;

import com.Ironhack.FinalProject.enums.AccountStatus;
import com.Ironhack.FinalProject.embeddables.Money;
import com.Ironhack.FinalProject.usermodels.AccountHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.time.LocalDate;
@Entity
public class Savings extends Account{
    @Embedded
 //   @DecimalMin("100")
    private Money minimumBalance = new Money(java.math.BigDecimal.valueOf(1000));
  //  @DecimalMax("0.5")
    private BigDecimal interestRate = new BigDecimal(0.0025);
    private LocalDate lastInterestDay = LocalDate.now();

    public Savings() {
    }

    public Savings(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, int secretKey) {
        super(balance, primaryOwner, secondaryOwner, secretKey);
        setLastInterestDay(lastInterestDay);
    }
    public Savings(Money balance, AccountHolder primaryOwner, int secretKey) {
        super(balance, primaryOwner, secretKey);
        setLastInterestDay(lastInterestDay);
    }


    public Savings(Money balance, AccountHolder owner, int secretKey, Money minimumBalance, BigDecimal interestRate) {
        super(balance, owner, secretKey);
        setMinimumBalance(minimumBalance);
        setInterestRate(interestRate);
    }

    public Savings(Money balance, AccountHolder owner, AccountHolder secondaryOwner, int secretKey, Money minimumBalance, BigDecimal interestRate) {
        super(balance, owner, secondaryOwner, secretKey);
        setMinimumBalance(minimumBalance);
        setInterestRate(interestRate);
    }

    public Money getMinimumBalance() {
        return minimumBalance;
    }

    public void setMinimumBalance(Money minimumBalance) {
        if(minimumBalance.getAmount().compareTo(BigDecimal.valueOf(1000))==1 || minimumBalance.getAmount().compareTo(BigDecimal.valueOf(100))==-1) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Minimum balance can not be greater than 1000 or smaller than 100");
        } else{
            this.minimumBalance = minimumBalance;
        }
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public LocalDate getLastInterestDay() {
        return lastInterestDay;
    }

    public void setLastInterestDay(LocalDate lastInterestDay) {
        this.lastInterestDay = lastInterestDay;
    }
}
