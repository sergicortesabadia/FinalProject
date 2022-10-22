package com.Ironhack.FinalProject.usermodels;



import javax.persistence.Column;
import javax.persistence.Entity;


@Entity
public class ThirdParty extends User{
    @Column(unique = true)
    private String hashedKey;



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

}
