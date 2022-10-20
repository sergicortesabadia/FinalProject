package com.Ironhack.FinalProject.usermodels;

import com.Ironhack.FinalProject.roles.Role;

import javax.persistence.*;
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
  //  @OneToMany(mappedBy = "user")
   // private Role role;

    public User() {
    }

    public User(String name) {
        setName(name);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
