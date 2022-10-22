package com.Ironhack.FinalProject.DTOs;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class CreateAccountDTO {
    @NotNull
    private Long id;
    @NotNull
    private String accountType;
    @NotNull
    private BigDecimal initialBalance;

    public CreateAccountDTO() {
    }

    public CreateAccountDTO(Long id, String accountType, BigDecimal initialBalance) {
        setId(id);
        setAccountType(accountType);
        setInitialBalance(initialBalance);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public BigDecimal getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(BigDecimal initialBalance) {
        this.initialBalance = initialBalance;
    }
}
