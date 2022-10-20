package com.Ironhack.FinalProject.DTOs;

import com.Ironhack.FinalProject.embeddables.Money;
import com.Ironhack.FinalProject.enums.AccountStatus;
import com.Ironhack.FinalProject.usermodels.AccountHolder;

import java.math.BigDecimal;
import java.time.LocalDate;

public class AccountDTO {

    private Long accountNumber;
    private Money balance;
    private String primaryOwner;
    private String secondaryOwner; //optional
    private int secretKey;
    private AccountStatus accountStatus;
    private Long accountHolderId;




    public AccountDTO(Long accountNumber, AccountStatus accountStatus) {
        setAccountNumber(accountNumber);
        setAccountStatus(accountStatus);
    }

    public AccountDTO(Long accountNumber, Money balance, String primaryOwner, String secondaryOwner, int secretKey, AccountStatus accountStatus) {
        setAccountNumber(accountNumber);
        setBalance(balance);
        setPrimaryOwner(primaryOwner);
        setSecondaryOwner(secondaryOwner);
        setSecretKey(secretKey);
        setAccountStatus(accountStatus);
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Money getBalance() {
        return balance;
    }

    public void setBalance(Money balance) {
        this.balance = balance;
    }

    public String getPrimaryOwner() {
        return primaryOwner;
    }

    public void setPrimaryOwner(String primaryOwner) {
        this.primaryOwner = primaryOwner;
    }

    public String getSecondaryOwner() {
        return secondaryOwner;
    }

    public void setSecondaryOwner(String secondaryOwner) {
        this.secondaryOwner = secondaryOwner;
    }

    public int getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(int secretKey) {
        this.secretKey = secretKey;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    public Long getAccountHolderId() {
        return accountHolderId;
    }

    public void setAccountHolderId(Long accountHolderId) {
        this.accountHolderId = accountHolderId;
    }
}
