package com.Ironhack.FinalProject.usermodels;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
public class Admin extends User{
    //@Column(unique = true)
    //@NotNull
    private String userName;
    //@Column(nullable = false)
    //@NotNull
    private String password;
    //roles


    public Admin() {
    }

    public Admin(String name, String userName, String password) {
        super(name);
        setUserName(userName);
        setPassword(password);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
