package com.example.timeupgrader;

import java.util.Date;

public class Account {
    private String id;
    private String email;
    private String username;
    private String password;
    private Date timeCreated;

    private static Account currentAccount;

    public Account(String id, String email, String username, String password, Date timeCreated) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.timeCreated = timeCreated;
        currentAccount = this;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Date getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Date timeCreated) {
        this.timeCreated = timeCreated;
    }

    public static Account getCurrentAccount() {
        return currentAccount;
    }
}
