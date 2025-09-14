package com.banking.system.model;

import java.time.LocalDateTime;

/**
 * User entity representing a bank account holder
 */
public class User {
    private String username;
    private String password;
    private String name;
    private String accountNumber;
    private String contactNumber;
    private int age;
    private String city;
    private double balance;
    private LocalDateTime createdAt;

    // Default constructor
    public User() {
        this.balance = 5000.0; // Default minimum balance
        this.createdAt = LocalDateTime.now();
    }

    // Constructor with parameters
    public User(String username, String password, String name, String accountNumber, 
                String contactNumber, int age, String city) {
        this();
        this.username = username;
        this.password = password;
        this.name = name;
        this.accountNumber = accountNumber;
        this.contactNumber = contactNumber;
        this.age = age;
        this.city = city;
    }

    // Getters and Setters
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return String.format("User{username='%s', name='%s', accountNumber='%s', balance=%.2f}", 
                           username, name, accountNumber, balance);
    }
}
