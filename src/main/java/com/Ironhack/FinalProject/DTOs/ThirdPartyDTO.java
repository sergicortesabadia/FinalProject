package com.Ironhack.FinalProject.DTOs;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class ThirdPartyDTO {
    @NotNull
    private BigDecimal amount;
    @NotNull
    private Long accountId;
    @NotNull
    private Long thirdPartyId;

    public ThirdPartyDTO() {
    }

    public ThirdPartyDTO(BigDecimal amount, Long accountId, Long thirdPartyId) {
        setAmount(amount);
        setAccountId(accountId);
        setThirdPartyId(thirdPartyId);
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

    public Long getThirdPartyId() {
        return thirdPartyId;
    }

    public void setThirdPartyId(Long thirdPartyId) {
        this.thirdPartyId = thirdPartyId;
    }
}
