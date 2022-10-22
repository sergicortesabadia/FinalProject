package com.Ironhack.FinalProject.DTOs;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class ModifyBalanceDTO {
    @NotNull
    private Long accountId;
    @NotNull
    private BigDecimal amount;

    public ModifyBalanceDTO() {
    }

    public ModifyBalanceDTO(Long accountId, BigDecimal amount) {
        setAccountId(accountId);
        setAmount(amount);
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
