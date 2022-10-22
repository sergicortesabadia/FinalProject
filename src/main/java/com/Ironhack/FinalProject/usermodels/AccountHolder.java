package com.Ironhack.FinalProject.usermodels;

import com.Ironhack.FinalProject.accountmodels.Account;
import com.Ironhack.FinalProject.embeddables.Address;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class AccountHolder extends User{
private String mail;
private Long phone;
private String name;
private LocalDate birthDate;
@Embedded
private Address primaryAddress;
@Embedded
@AttributeOverrides({
        @AttributeOverride(name= "street", column = @Column(name = "mail_street")),
        @AttributeOverride(name = "city", column = @Column(name = "mail_city")),
        @AttributeOverride(name = "postalCode", column = @Column(name = "mail_postal_code")),
        @AttributeOverride(name = "provinceState", column = @Column(name = "mail_province_state")),
        @AttributeOverride(name = "country", column = @Column(name = "mail_country"))
})
private Address mailingAddress;
@OneToMany(mappedBy = "primaryOwner")
@JsonIgnore
private List<Account> primaryAccountHolderList;

@OneToMany(mappedBy = "secondaryOwner")
@JsonIgnore
private List <Account> secondaryAccountHolderList;
//roles


    public AccountHolder() {
    }

    public AccountHolder(String username, String mail, Long phone, String name, LocalDate birthDate, Address primaryAddress) {
        super(username);
        setMail(mail);
        setPhone(phone);
        setName(name);
        setBirthDate(birthDate);
        setPrimaryAddress(primaryAddress);
    }
    public AccountHolder(String username, String password, String mail, Long phone, String name, LocalDate birthDate, Address primaryAddress) {
        super(username, password);
        setMail(mail);
        setPhone(phone);
        setName(name);
        setBirthDate(birthDate);
        setPrimaryAddress(primaryAddress);
    }

    public AccountHolder(String username, String password, String mail, Long phone, String name, LocalDate birthDate, Address primaryAddress, Address mailingAddress) {
        super(username, password);
        setMail(mail);
        setPhone(phone);
        setName(name);
        setBirthDate(birthDate);
        setPrimaryAddress(primaryAddress);
        setMailingAddress(mailingAddress);
    }

    public AccountHolder(String username, String password, String mail, Long phone, String name, LocalDate birthDate, Address primaryAddress, Address mailingAddress, List<Account> primaryAccountHolderList, List<Account> secondaryAccountHolderList) {
        super(username, password);
        setMail(mail);
        setPhone(phone);
        setName(name);
        setBirthDate(birthDate);
        setPrimaryAddress(primaryAddress);
        setMailingAddress(mailingAddress);
        setPrimaryAccountHolderList(primaryAccountHolderList);
        setSecondaryAccountHolderList(secondaryAccountHolderList);
    }

    public AccountHolder(String username, String password, String mail, Long phone, String name, LocalDate birthDate, Address primaryAddress, List<Account> primaryAccountHolderList, List<Account> secondaryAccountHolderList) {
        super(username, password);
        setMail(mail);
        setPhone(phone);
        setName(name);
        setBirthDate(birthDate);
        setPrimaryAddress(primaryAddress);
        setPrimaryAccountHolderList(primaryAccountHolderList);
        setSecondaryAccountHolderList(secondaryAccountHolderList);
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public List<Account> getPrimaryAccountHolderList() {
        return primaryAccountHolderList;
    }

    public void setPrimaryAccountHolderList(List<Account> primaryAccountHolderList) {
        this.primaryAccountHolderList = primaryAccountHolderList;
    }

    public List<Account> getSecondaryAccountHolderList() {
        return secondaryAccountHolderList;
    }

    public void setSecondaryAccountHolderList(List<Account> secondaryAccountHolderList) {
        this.secondaryAccountHolderList = secondaryAccountHolderList;
    }

}
