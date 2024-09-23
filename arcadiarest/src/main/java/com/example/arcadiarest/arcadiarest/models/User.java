package com.example.arcadiarest.arcadiarest.models;

import java.util.Objects;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;


@Entity
public class User {

    private @Id
    @GeneratedValue Long id;
    private String username;
    private String email;

    User(){}

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public Long getId() {
        return this.id;
    }

    public String getUsername() {
        return this.username;
    }

    public String getEmail() {
        return this.email;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Override 
    public boolean equals(Object o) {
        if(this == o)
            return true;
        if(!(o instanceof User))
            return false;
        User user = (User) o;

        return Objects.equals(this.id, user.id)
            && Objects.equals(this.username, user.username)
            && Objects.equals(this.email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.username, this.email);
    }

    @Override
    public String toString() {
        return "User{" + "id=" + this.id + ", username=" + this.username + ", email=" + this.email;
    }

}
