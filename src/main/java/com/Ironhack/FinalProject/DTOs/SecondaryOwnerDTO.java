package com.Ironhack.FinalProject.DTOs;

public class SecondaryOwnerDTO {

    private Long accountNumber;
    private Long accountHolderId;

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
