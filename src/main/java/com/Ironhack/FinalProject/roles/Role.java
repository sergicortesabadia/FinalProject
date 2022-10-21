package com.Ironhack.FinalProject.roles;

import com.Ironhack.FinalProject.usermodels.User;

import javax.persistence.*;

@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @Enumerated
    private RolesEnum role;
   @ManyToOne
   private User user;

    public Role() {
    }

    public Role(RolesEnum role, User user) {
        setRole(role);
        setUser(user);
    }

    public Long getId() {
        return id;
    }


    public RolesEnum getRole() {
        return role;
    }

    public void setRole(RolesEnum role) {
        this.role = role;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
