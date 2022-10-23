package com.Ironhack.FinalProject.DTOs;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public class AccountHolderCreationDTO {
    @NotNull
    private String street;
    @NotNull
    private String city;

    private String postalCode;

    private String provinceState;
    @NotNull
    private String country;
    @NotNull
    private String username;
    @NotNull
    private String name;
    @NotNull
    private String mail;
    @NotNull
    private Long phone;
    @NotNull
    private LocalDate birthDate;
    @NotNull
    private String accountType;
    @NotNull
    private BigDecimal initialBalance;

    public AccountHolderCreationDTO() {
    }

    public AccountHolderCreationDTO(String street, String city, String postalCode, String provinceState, String country, String username, String name, String mail, Long phone, LocalDate birthDate) {
        setStreet(street);
        setCity(city);
        setPostalCode(postalCode);
        setProvinceState(provinceState);
        setCountry(country);
        setUsername(username);
        setName(name);
        setMail(mail);
        setPhone(phone);
        setBirthDate(birthDate);
    }

    public AccountHolderCreationDTO(String street, String city, String postalCode, String provinceState, String country, String username, String name, String mail, Long phone, @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate birthDate, String accountType, BigDecimal initialBalance) {
        setStreet(street);
        setCity(city);
        setPostalCode(postalCode);
        setProvinceState(provinceState);
        setCountry(country);
        setUsername(username);
        setName(name);
        setMail(mail);
        setPhone(phone);
        setBirthDate(birthDate);
        setAccountType(accountType);
        setInitialBalance(initialBalance);
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getProvinceState() {
        return provinceState;
    }

    public void setProvinceState(String provinceState) {
        this.provinceState = provinceState;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
