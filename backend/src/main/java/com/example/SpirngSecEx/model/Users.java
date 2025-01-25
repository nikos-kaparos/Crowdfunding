package com.example.SpirngSecEx.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED) // Make table for any entity
public class Users {
    // Δημιουργία των views για τον περιορισμό των δεδομένων που θα επιστραφούν σε κάθε context
    // Η Public view περιλαμβάνει τα βασικά πεδία που είναι προσβάσιμα από όλους
    public static class Public {}
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Users.Public.class)
    private int id;
    @Column
    @JsonView(Users.Public.class)
    private String username;
    @Column
    @JsonIgnore
    private String password;
    @Column
    @JsonView(Users.Public.class)
    private String email;
    @Column
    @JsonView(Users.Public.class)
    private String role;
    @Column
    @JsonView(Users.Public.class)
    private boolean enabled;

    public Users() {
    }
    public Users(String username) {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", unsername='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
