package com.Ironhack.FinalProject.usermodels;

import com.Ironhack.FinalProject.roles.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Inheritance(strategy = InheritanceType.JOINED)
@Entity
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String username;
    @Column(nullable = false)
    private String password;

   @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
   @JsonIgnore
   private List<Role> roles = new ArrayList<>();

    public User() {
    }

    public User(String username, String password) {
        setUsername(username);
        setPassword(password);
    }
    public User(String username) {
        setUsername(username);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }
    public void addRole(Role role){
        this.roles.add(role);
    }
}
