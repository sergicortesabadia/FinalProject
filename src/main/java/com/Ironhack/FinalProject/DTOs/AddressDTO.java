package com.Ironhack.FinalProject.DTOs;

import javax.validation.constraints.NotNull;

public class AddressDTO {
    private Long accountHolderId;
    @NotNull
    private String street;
    @NotNull
    private String city;
    private String postalCode;
    private String provinceState;
    @NotNull
    private String country;

    public AddressDTO() {
    }

    public AddressDTO(String street, String city, String postalCode, String provinceState, String country) {
        setStreet(street);
        setCity(city);
        setPostalCode(postalCode);
        setProvinceState(provinceState);
        setCountry(country);
    }

    public AddressDTO(Long accountHolderId, String street, String city, String postalCode, String provinceState, String country) {
        setAccountHolderId(accountHolderId);
        setStreet(street);
        setCity(city);
        setPostalCode(postalCode);
        setProvinceState(provinceState);
        setCountry(country);
    }

    public Long getAccountHolderId() {
        return accountHolderId;
    }

    public void setAccountHolderId(Long accountHolderId) {
        this.accountHolderId = accountHolderId;
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
}
