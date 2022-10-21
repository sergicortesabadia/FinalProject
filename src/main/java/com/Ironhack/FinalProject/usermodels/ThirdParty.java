package com.Ironhack.FinalProject.usermodels;

import com.Ironhack.FinalProject.transactions.Transaction;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class ThirdParty extends User{
    @Column(unique = true)
    private String hashedKey;
  //  @OneToMany(mappedBy = "thirdParty")
    // List<Transaction> thirdPartyTransactionList;


    public ThirdParty() {
    }

    public ThirdParty(String username, String password, String hashedKey) {
        super(username, password);
        setHashedKey(hashedKey);
    }

    public String getHashedKey() {
        return hashedKey;
    }

    public void setHashedKey(String hashedKey) {
        this.hashedKey = hashedKey;
    }

/*    public List<Transaction> getThirdPartyTransactionList() {
        return thirdPartyTransactionList;
    }

    public void setThirdPartyTransactionList(List<Transaction> thirdPartyTransactionList) {
        this.thirdPartyTransactionList = thirdPartyTransactionList;
    }*/
}
