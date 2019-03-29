package com.example.timeupgrader;

public class Email {

    public static Email currentEmail;
    private String email;

    Email(String email) {
        this.email = email;
        currentEmail = this;
    }

    public String getEmail() {
        return email;
    }

    public static Email getCurrentEmail() {
        return currentEmail;
    }
}
