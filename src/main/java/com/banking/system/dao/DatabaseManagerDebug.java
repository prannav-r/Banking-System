package com.banking.system.dao;

import com.banking.system.config.DatabaseConfigDebug;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Database connection and management class - Debug version
 */
public class DatabaseManagerDebug {

    public DatabaseManagerDebug() {
        initializeDatabase();
    }

    /**
     * Initialize database connection and create tables if they don't exist
     */
    private void initializeDatabase() {
        System.out.println("=== DEBUG MODE: Database Connection ===");
        DatabaseConfigDebug.printConfiguration();

        try {
            // Create database if it doesn't exist
            createDatabaseIfNotExists();
            
            // Create table if it doesn't exist
            createTableIfNotExists();
            
            System.out.println("Database connection established successfully!");
            
        } catch (SQLException e) {
            System.err.println("Database initialization error: " + e.getMessage());
            System.err.println("Error details: " + e.getClass().getSimpleName());
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Create database if it doesn't exist
     */
    private void createDatabaseIfNotExists() throws SQLException {
        System.out.println("\n=== Creating Database ===");
        String url = DatabaseConfigDebug.buildCreateDatabaseUrl();
        String username = DatabaseConfigDebug.getUsername();
        String password = DatabaseConfigDebug.getPassword();
        
        System.out.println("Connection URL: " + url);
        System.out.println("Username: " + username);
        System.out.println("Password: " + (password.isEmpty() ? "[empty]" : "[set - length: " + password.length() + "]"));
        
        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement stmt = conn.createStatement()) {
            
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DatabaseConfigDebug.getDatabaseName());
            stmt.executeUpdate("USE " + DatabaseConfigDebug.getDatabaseName());
            System.out.println("Database created/selected successfully");
        }
    }

    /**
     * Create userdata table if it doesn't exist
     */
    private void createTableIfNotExists() throws SQLException {
        System.out.println("\n=== Creating Table ===");
        String createTableSql = """
            CREATE TABLE IF NOT EXISTS userdata (
                username VARCHAR(30) PRIMARY KEY,
                password VARCHAR(30) NOT NULL,
                name VARCHAR(30) NOT NULL,
                actno VARCHAR(5) UNIQUE NOT NULL,
                contactno VARCHAR(10) NOT NULL,
                age INT NOT NULL,
                city VARCHAR(10) NOT NULL,
                balance DOUBLE DEFAULT 5000.0
            )
            """;
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            
            stmt.executeUpdate(createTableSql);
            System.out.println("Table created successfully");
        }
    }

    /**
     * Get database connection
     */
    public Connection getConnection() throws SQLException {
        String url = DatabaseConfigDebug.buildDatabaseUrl();
        String username = DatabaseConfigDebug.getUsername();
        String password = DatabaseConfigDebug.getPassword();
        
        System.out.println("\n=== Getting Connection ===");
        System.out.println("URL: " + url);
        System.out.println("Username: " + username);
        System.out.println("Password: " + (password.isEmpty() ? "[empty]" : "[set - length: " + password.length() + "]"));
        
        return DriverManager.getConnection(url, username, password);
    }
}
