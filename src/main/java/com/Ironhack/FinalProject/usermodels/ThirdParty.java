package com.Ironhack.FinalProject.usermodels;

import com.Ironhack.FinalProject.transactions.Transaction;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class ThirdParty extends User{

    private String hashedKey;
  //  @OneToMany(mappedBy = "thirdParty")
    // List<Transaction> thirdPartyTransactionList;


    public ThirdParty() {
    }

    public ThirdParty(String name, String hashedKey) {
        super(name);
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
