package com.Ironhack.FinalProject.DTOs;

import java.math.BigDecimal;

public class ThirdPartyDTO {
    private String hashedKey;
    private BigDecimal amount;
    private Long accountId;

    public ThirdPartyDTO(String hashedKey, BigDecimal amount, Long accountId) {
        setHashedKey(hashedKey);
        setAmount(amount);
        setAmount(amount);
    }

    public String getHashedKey() {
        return hashedKey;
    }

    public void setHashedKey(String hashedKey) {
        this.hashedKey = hashedKey;
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
