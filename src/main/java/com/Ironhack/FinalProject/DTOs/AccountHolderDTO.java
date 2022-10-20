package com.Ironhack.FinalProject.DTOs;

import com.Ironhack.FinalProject.embeddables.Address;
import com.Ironhack.FinalProject.embeddables.Money;

import java.math.BigDecimal;
import java.time.LocalDate;

public class AccountHolderDTO {
        private Long id;
        private String name;
        private String mail;
        private Long phone;
        private LocalDate birthDate;
        private Address primaryAddress;
        private Address mailingAddress;
        private String accountType;
        private BigDecimal initialBalance;

    public AccountHolderDTO(Long id, String accountType, BigDecimal initialBalance) {
        setId(id);
        setAccountType(accountType);
        setInitialBalance(initialBalance);
    }

    public AccountHolderDTO(String name, String mail, Long phone, LocalDate birthDate, Address primaryAddress, String accountType, BigDecimal initialBalance) {
        setName(name);
        setMail(mail);
        setPhone(phone);
        setBirthDate(birthDate);
        setPrimaryAddress(primaryAddress);
        setAccountType(accountType);
        setInitialBalance(initialBalance);
    }

    public AccountHolderDTO(String name, String mail, Long phone, LocalDate birthDate, Address primaryAddress, Address mailingAddress, String accountType, BigDecimal initialBalance) {
        setName(name);
        setMail(mail);
        setPhone(phone);
        setBirthDate(birthDate);
        setPrimaryAddress(primaryAddress);
        setMailingAddress(mailingAddress);
        setAccountType(accountType);
        setInitialBalance(initialBalance);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Address getPrimaryAddress() {
        return primaryAddress;
    }

    public void setPrimaryAddress(Address primaryAddress) {
        this.primaryAddress = primaryAddress;
    }

    public Address getMailingAddress() {
        return mailingAddress;
    }

    public void setMailingAddress(Address mailingAddress) {
        this.mailingAddress = mailingAddress;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

