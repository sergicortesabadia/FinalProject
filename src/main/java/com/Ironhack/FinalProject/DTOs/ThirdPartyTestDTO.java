package com.Ironhack.FinalProject.DTOs;

import java.math.BigDecimal;

public class ThirdPartyTestDTO {
    private BigDecimal amount;
    private Long accountId;
    private String hashedKey;

    public ThirdPartyTestDTO(BigDecimal amount, Long accountId, String hashedKey) {
        setAmount(amount);
        setAccountId(accountId);
        setHashedKey(hashedKey);

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

    public String getHashedKey() {
        return hashedKey;
    }

    public void setHashedKey(String hashedKey) {
        this.hashedKey = hashedKey;
    }
}
