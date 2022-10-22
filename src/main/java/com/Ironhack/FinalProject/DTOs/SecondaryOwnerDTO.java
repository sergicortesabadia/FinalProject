package com.Ironhack.FinalProject.DTOs;

import javax.validation.constraints.NotNull;

public class SecondaryOwnerDTO {
    @NotNull
    private Long accountNumber;
    @NotNull
    private Long accountHolderId;

    public SecondaryOwnerDTO() {
    }

    public SecondaryOwnerDTO(Long accountNumber, Long accountHolderId) {
        setAccountNumber(accountNumber);
        setAccountHolderId(accountHolderId);
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Long getAccountHolderId() {
        return accountHolderId;
    }

    public void setAccountHolderId(Long accountHolderId) {
        this.accountHolderId = accountHolderId;
    }
}
