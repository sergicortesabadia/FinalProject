package com.Ironhack.FinalProject.DTOs;

import java.math.BigDecimal;

public class ThirdPartyDTO {
    private BigDecimal amount;
    private Long accountId;

    public ThirdPartyDTO() {
    }

    public ThirdPartyDTO(BigDecimal amount, Long accountId) {
        setAmount(amount);
        setAccountId(accountId);
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

}
