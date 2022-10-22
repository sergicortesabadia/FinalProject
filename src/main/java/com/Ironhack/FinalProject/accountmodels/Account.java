package com.Ironhack.FinalProject.accountmodels;


import com.Ironhack.FinalProject.enums.AccountStatus;
import com.Ironhack.FinalProject.embeddables.Money;
import com.Ironhack.FinalProject.transactions.Transaction;
import com.Ironhack.FinalProject.usermodels.AccountHolder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Inheritance(strategy = InheritanceType.JOINED)
@Entity
public abstract class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountNumber;
    @Embedded
    private Money balance;
    @ManyToOne
    @JoinColumn(name = "primary_owner")
    private AccountHolder primaryOwner;
    @ManyToOne
    @JoinColumn(name = "secondary_owner")
    private AccountHolder secondaryOwner;
    private int secretKey;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="currency", column = @Column(name = "fee_currency")),
            @AttributeOverride(name = "amount", column = @Column(name = "fee_amount"))
    })
    private final Money penaltyFee = new Money(BigDecimal.valueOf(40));
    private final LocalDate creationDate = LocalDate.now();
    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus = AccountStatus.ACTIVE;
    @OneToMany(mappedBy = "accountSent")
    private List<Transaction> sentTransactionList = new ArrayList<>();
    @OneToMany(mappedBy = "accountReceived")
    private List<Transaction> receivedTransactionList = new ArrayList<>();



    public Account() {
    }

    public Account(AccountHolder primaryOwner) {
        setPrimaryOwner(primaryOwner);
    }

    public Account(Money balance, AccountHolder primaryOwner, int secretKey) {
        setBalance(balance);
        setPrimaryOwner(primaryOwner);
        setSecretKey(secretKey);
    }

    public Account(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, int secretKey) {
        setBalance(balance);
        setPrimaryOwner(primaryOwner);
        setSecondaryOwner(secondaryOwner);
        setSecretKey(secretKey);
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

    public AccountHolder getPrimaryOwner() {
        return primaryOwner;
    }

    public void setPrimaryOwner(AccountHolder primaryOwner) {
        this.primaryOwner = primaryOwner;
    }

    public AccountHolder getSecondaryOwner() {
        return secondaryOwner;
    }

    public void setSecondaryOwner(AccountHolder secondaryOwner) {
        this.secondaryOwner = secondaryOwner;
    }

    public int getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(int secretKey) {
        this.secretKey = secretKey;
    }

    public Money getPenaltyFee() {
        return penaltyFee;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    public List<Transaction> getSentTransactionList() {
        return sentTransactionList;
    }

    public void setSentTransactionList(List<Transaction> sentTransactionList) {
        this.sentTransactionList = sentTransactionList;
    }

    public List<Transaction> getReceivedTransactionList() {
        return receivedTransactionList;
    }

    public void setReceivedTransactionList(List<Transaction> receivedTransactionList) {
        this.receivedTransactionList = receivedTransactionList;
    }
}
