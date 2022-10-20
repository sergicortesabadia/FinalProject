package com.Ironhack.FinalProject.transactions;

import com.Ironhack.FinalProject.accountmodels.Account;
import com.Ironhack.FinalProject.enums.TransactionType;
import com.Ironhack.FinalProject.usermodels.ThirdParty;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    private Long senderId;
    private Long receiverId;
    private BigDecimal amount;
    private LocalDateTime transferDate;
    @ManyToOne
    private Account accountSent;
    @ManyToOne
    private Account accountReceived;
    //@ManyToOne
    //private ThirdParty thirdParty;

    public Transaction() {
    }

    public Transaction(TransactionType transactionType, Long senderId, Long receiverId, BigDecimal amount, LocalDateTime transferDate, Account accountSent, Account accountReceived) {
        setTransactionType(transactionType);
        setSenderId(senderId);
        setReceiverId(receiverId);
        setAmount(amount);
        setTransferDate(transferDate);
        setAccountSent(accountSent);
        setAccountReceived(accountReceived);
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(LocalDateTime transferDate) {
        this.transferDate = transferDate;
    }

    public Account getAccountSent() {
        return accountSent;
    }

    public void setAccountSent(Account accountSent) {
        this.accountSent = accountSent;
    }

    public Account getAccountReceived() {
        return accountReceived;
    }

    public void setAccountReceived(Account accountReceived) {
        this.accountReceived = accountReceived;
    }

 /*   public ThirdParty getThirdParty() {
        return thirdParty;
    }

    public void setThirdParty(ThirdParty thirdParty) {
        this.thirdParty = thirdParty;
    }*/
}
