package com.banking.system.service;

import com.banking.system.model.User;
import com.banking.system.dao.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Service class for banking operations
 */
public class BankingService {
    private DatabaseManager databaseManager;
    private User currentUser;

    public BankingService() {
        this.databaseManager = new DatabaseManager();
    }

    /**
     * Authenticate user login
     */
    public User login(String username, String password) {
        String sql = "SELECT * FROM userdata WHERE username = ? AND password = ?";
        
        try (Connection conn = databaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, username);
            stmt.setString(2, password);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    currentUser = new User();
                    currentUser.setUsername(rs.getString("username"));
                    currentUser.setPassword(rs.getString("password"));
                    currentUser.setName(rs.getString("name"));
                    currentUser.setAccountNumber(rs.getString("actno"));
                    currentUser.setContactNumber(rs.getString("contactno"));
                    currentUser.setAge(rs.getInt("age"));
                    currentUser.setCity(rs.getString("city"));
                    currentUser.setBalance(rs.getDouble("balance"));
                    return currentUser;
                }
            }
        } catch (SQLException e) {
            System.err.println("Login error: " + e.getMessage());
        }
        return null;
    }

    /**
     * Register a new user
     */
    public boolean register(User user) {
        // Check if username already exists
        if (userExists(user.getUsername())) {
            return false;
        }

        // Generate account number
        String accountNumber = generateAccountNumber();
        user.setAccountNumber(accountNumber);

        String sql = "INSERT INTO userdata (username, password, name, actno, contactno, age, city, balance) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = databaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getName());
            stmt.setString(4, user.getAccountNumber());
            stmt.setString(5, user.getContactNumber());
            stmt.setInt(6, user.getAge());
            stmt.setString(7, user.getCity());
            stmt.setDouble(8, user.getBalance());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Registration error: " + e.getMessage());
            return false;
        }
    }

    /**
     * Deposit amount to current user's account
     */
    public boolean deposit(double amount) {
        if (amount <= 0) {
            return false;
        }

        String sql = "UPDATE userdata SET balance = balance + ? WHERE username = ?";
        
        try (Connection conn = databaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setDouble(1, amount);
            stmt.setString(2, currentUser.getUsername());
            
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                currentUser.setBalance(currentUser.getBalance() + amount);
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Deposit error: " + e.getMessage());
        }
        return false;
    }

    /**
     * Get current user's balance
     */
    public double getBalance() {
        return currentUser.getBalance();
    }

    /**
     * Transfer amount to another account
     */
    public boolean transfer(String recipientAccountNumber, double amount) {
        if (amount <= 0 || amount > currentUser.getBalance()) {
            return false;
        }

        // Check if recipient account exists
        if (!accountExists(recipientAccountNumber)) {
            return false;
        }

        try (Connection conn = databaseManager.getConnection()) {
            conn.setAutoCommit(false); // Start transaction
            
            try {
                // Deduct from sender
                String deductSql = "UPDATE userdata SET balance = balance - ? WHERE username = ?";
                try (PreparedStatement deductStmt = conn.prepareStatement(deductSql)) {
                    deductStmt.setDouble(1, amount);
                    deductStmt.setString(2, currentUser.getUsername());
                    deductStmt.executeUpdate();
                }

                // Add to recipient
                String addSql = "UPDATE userdata SET balance = balance + ? WHERE actno = ?";
                try (PreparedStatement addStmt = conn.prepareStatement(addSql)) {
                    addStmt.setDouble(1, amount);
                    addStmt.setString(2, recipientAccountNumber);
                    addStmt.executeUpdate();
                }

                conn.commit(); // Commit transaction
                currentUser.setBalance(currentUser.getBalance() - amount);
                return true;
                
            } catch (SQLException e) {
                conn.rollback(); // Rollback on error
                throw e;
            }
        } catch (SQLException e) {
            System.err.println("Transfer error: " + e.getMessage());
            return false;
        }
    }

    /**
     * Change password for current user
     */
    public boolean changePassword(String currentPassword, String newPassword) {
        if (!currentPassword.equals(currentUser.getPassword())) {
            return false;
        }

        String sql = "UPDATE userdata SET password = ? WHERE username = ?";
        
        try (Connection conn = databaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, newPassword);
            stmt.setString(2, currentUser.getUsername());
            
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                currentUser.setPassword(newPassword);
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Password change error: " + e.getMessage());
        }
        return false;
    }

    /**
     * Delete current user's account
     */
    public boolean deleteAccount(String password) {
        if (!password.equals(currentUser.getPassword())) {
            return false;
        }

        String sql = "DELETE FROM userdata WHERE username = ?";
        
        try (Connection conn = databaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, currentUser.getUsername());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Account deletion error: " + e.getMessage());
            return false;
        }
    }

    /**
     * Get current user's account details
     */
    public User getAccountDetails() {
        return currentUser;
    }

    /**
     * Get current logged-in user
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Check if user exists
     */
    public boolean userExists(String username) {
        String sql = "SELECT COUNT(*) FROM userdata WHERE username = ?";
        
        try (Connection conn = databaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, username);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("User existence check error: " + e.getMessage());
        }
        return false;
    }

    /**
     * Check if account number exists
     */
    private boolean accountExists(String accountNumber) {
        String sql = "SELECT COUNT(*) FROM userdata WHERE actno = ?";
        
        try (Connection conn = databaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, accountNumber);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Account existence check error: " + e.getMessage());
        }
        return false;
    }

    /**
     * Generate random account number
     */
    private String generateAccountNumber() {
        Random random = new Random();
        return String.valueOf(random.nextInt(90000) + 10000);
    }


    /**
     * Get user by username
     */
    public User getUserByUsername(String username) {
        String sql = "SELECT * FROM userdata WHERE username = ?";
        
        try (Connection conn = databaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, username);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new User(
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("full_name"),
                        rs.getString("account_number"),
                        rs.getString("contact_no"),
                        rs.getInt("age"),
                        rs.getString("city"),
                        rs.getDouble("balance")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Get user error: " + e.getMessage());
        }
        return null;
    }

    /**
     * Logout current user
     */
    public void logout() {
        currentUser = null;
    }
}
