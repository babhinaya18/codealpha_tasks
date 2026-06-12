package com.stocktrading;

public class User {
    private String username;
    private String fullName;
    private String email;
    private double balance;

    public User(String username, String fullName, String email, double balance) {
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.balance = balance;
    }

    public String getUsername() { return username; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public double getBalance() { return balance; }

    public void setBalance(double balance) { this.balance = balance; }
}